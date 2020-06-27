package io.gomint.server.network;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.jni.NativeCode;
import io.gomint.server.jni.zlib.JavaZLib;
import io.gomint.server.jni.zlib.NativeZLib;
import io.gomint.server.jni.zlib.ZLib;
import io.gomint.server.maintenance.ReportUploader;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketBatch;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.zip.Adler32;
import java.util.zip.DataFormatException;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PostProcessWorker implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostProcessWorker.class);
    private static final NativeCode<ZLib> ZLIB = new NativeCode<>("zlib", JavaZLib.class, NativeZLib.class);
    private static final ThreadLocal<ZLib> COMPRESSOR = new ThreadLocal<>();

    static {
        ZLIB.load();
    }

    private final ConnectionWithState connection;
    private final Packet[] packets;

    public PostProcessWorker(ConnectionWithState connection, Packet[] packets) {
        this.connection = connection;
        this.packets = packets;
    }

    private ZLib getCompressor() {
        ZLib zLib = COMPRESSOR.get();
        if (zLib != null) {
            return zLib;
        }

        zLib = ZLIB.newInstance();
        zLib.init(true, false, 7);
        COMPRESSOR.set(zLib);
        return zLib;
    }

    @Override
    public void run() {
        ByteBuf inBuf = writePackets(this.packets);
        if (inBuf.readableBytes() == 0) {
            inBuf.release();
            return;
        }

        ByteBuf data = compress(inBuf);
        inBuf.release();
        if (data == null) {
            return;
        }

        PacketBatch batch = this.encrypt(data);
        this.connection.send(batch);
    }

    private PacketBatch encrypt(ByteBuf data) {
        PacketBatch batch = new PacketBatch();
        batch.setPayload(data);

        EncryptionHandler encryptionHandler = this.connection.getEncryptionHandler();
        if (encryptionHandler != null && (!this.connection.isPlayer() || this.connection.getState() == PlayerConnectionState.LOGIN || this.connection.getState() == PlayerConnectionState.PLAYING)) {
            ByteBuf buffer = batch.getPayload();
            batch.setPayload(this.connection.isPlayer() ? encryptionHandler.encryptInputForClient(buffer.nioBuffer()) : encryptionHandler.encryptInputForServer(buffer.nioBuffer()));
            buffer.release();
        }

        return batch;
    }

    private ByteBuf writePackets(Packet[] packets) {
        // Write all packets into the inBuf for compression
        PacketBuffer buffer = new PacketBuffer(16);
        ByteBuf inBuf = newNettyBuffer();

        for (Packet packet : packets) {
            if (packet instanceof PacketBatch) { // Only chunks can do this
                PacketBatch batch = (PacketBatch) packet;
                if (!batch.isCompressed()) {
                    ByteBuf in = newNettyBuffer();
                    in.writeBytes(batch.getPayload());
                    batch.setPayload(this.compress(in));
                    in.release();
                    batch.setCompressed(true);
                }

                batch.getPayload().retain(); // The encryption releases the old payload once
                PacketBatch encrypted = this.encrypt(batch.getPayload());
                this.connection.send(encrypted);
            } else {
                buffer.setReadPosition(0);
                buffer.setWritePosition(0);

                // CHECKSTYLE:OFF
                try {
                    packet.serializeHeader(buffer);
                    packet.serialize(buffer, this.connection.getProtocolID());

                    writeVarInt(buffer.getWritePosition(), inBuf);
                    inBuf.writeBytes(buffer.getBuffer());
                } catch (Exception e) {
                    LOGGER.error("Could not serialize packet", e);
                    ReportUploader.create().tag("network.serialize").exception(e).upload();
                }
                // CHECKSTYLE:ON
            }
        }

        return inBuf;
    }

    private ByteBuf newNettyBuffer() {
        return PooledByteBufAllocator.DEFAULT.directBuffer();
    }

    private ByteBuf compress(ByteBuf inBuf) {
        if (inBuf.readableBytes() > 256 || !this.connection.isPlayer()) {
            return zlibCompress(inBuf);
        } else {
            return fastStorage(inBuf);
        }
    }

    private ByteBuf zlibCompress(ByteBuf inBuf) {
        ZLib compressor = this.getCompressor();
        ByteBuf outBuf = newNettyBuffer();

        try {
            compressor.process(inBuf.nioBuffer(0, inBuf.readableBytes()), outBuf);
        } catch (DataFormatException e) {
            LOGGER.error("Could not compress data for network", e);
            outBuf.release();
            return null;
        }

        return outBuf;
    }

    private ByteBuf fastStorage(ByteBuf inBuf) {
        ByteBuf output = PooledByteBufAllocator.DEFAULT.directBuffer(inBuf.readableBytes() + 7 + 4);

        // Zlib magic for fast storage
        output.writeByte(0x78);
        output.writeByte(0x01);
        output.writeByte(0x01);

        // Write data length
        int length = inBuf.readableBytes();
        output.writeByte((byte) length);
        output.writeByte((byte) (length >>> 8));
        length = ~length;
        output.writeByte((byte) length);
        output.writeByte((byte) (length >>> 8));

        // Write data
        output.writeBytes(inBuf);

        long checksum = adler32(inBuf.nioBuffer(0, ~length));
        output.writeByte(((byte) ((checksum >> 24) % 256)));
        output.writeByte(((byte) ((checksum >> 16) % 256)));
        output.writeByte(((byte) ((checksum >> 8) % 256)));
        output.writeByte(((byte) (checksum % 256)));
        return output;
    }

    /**
     * Calculates the adler32 checksum of the data
     */
    private long adler32(ByteBuffer data) {
        final Adler32 checksum = new Adler32();
        checksum.update(data);
        return checksum.getValue();
    }

    private void writeVarInt(int value, ByteBuf stream) {
        while ((value & -128) != 0) {
            stream.writeByte(value & 127 | 128);
            value >>>= 7;
        }

        stream.writeByte(value);
    }

}
