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

        byte[] data = compress(inBuf);
        inBuf.release();
        if (data == null) {
            return;
        }

        PacketBatch batch = this.encrypt(data);
        this.connection.send(batch);
    }

    private PacketBatch encrypt(byte[] data) {
        PacketBatch batch = new PacketBatch();
        batch.setPayload(data);
        batch.setPayloadLength(data.length);

        EncryptionHandler encryptionHandler = this.connection.getEncryptionHandler();
        if (encryptionHandler != null && (!this.connection.isPlayer() || this.connection.getState() == PlayerConnectionState.LOGIN || this.connection.getState() == PlayerConnectionState.PLAYING)) {
            batch.setPayload(this.connection.isPlayer() ? encryptionHandler.encryptInputForClient(batch.getPayload()) : encryptionHandler.encryptInputForServer(batch.getPayload()));
            batch.setPayloadLength(batch.getPayload().length);
        }

        return batch;
    }

    private ByteBuf writePackets(Packet[] packets) {
        ByteBuf inBuf = newNettyBuffer();

        // Write all packets into the inBuf for compression
        PacketBuffer buffer = new PacketBuffer(16);

        for (Packet packet : packets) {
            if (packet instanceof PacketBatch) { // Only chunks can do this
                PacketBatch batch = (PacketBatch) packet;
                if (!batch.isCompressed()) {
                    ByteBuf in = newNettyBuffer();
                    in.writeBytes(batch.getPayload());
                    batch.setPayload(this.compress(in));
                    batch.setPayloadLength(batch.getPayload().length);
                    in.release();
                    batch.setCompressed(true);
                }

                PacketBatch encrypted = this.encrypt(batch.getPayload());
                this.connection.send(encrypted);
            } else {
                buffer.setPosition(0);

                // CHECKSTYLE:OFF
                try {
                    packet.serializeHeader(buffer);
                    packet.serialize(buffer, this.connection.getProtocolID());

                    writeVarInt(buffer.getPosition(), inBuf);
                    inBuf.writeBytes(buffer.getBuffer(), buffer.getBufferOffset(), buffer.getPosition() - buffer.getBufferOffset());
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

    private byte[] compress(ByteBuf inBuf) {
        if (inBuf.readableBytes() > 256 || !this.connection.isPlayer()) {
            return zlibCompress(inBuf);
        } else {
            return fastStorage(inBuf);
        }
    }

    private byte[] zlibCompress(ByteBuf inBuf) {
        ZLib compressor = this.getCompressor();
        ByteBuf outBuf = newNettyBuffer();

        try {
            compressor.process(inBuf, outBuf);
        } catch (DataFormatException e) {
            LOGGER.error("Could not compress data for network", e);
            outBuf.release();
            return null;
        }

        byte[] data = new byte[outBuf.readableBytes()];
        outBuf.readBytes(data);
        outBuf.release();
        return data;
    }

    private byte[] fastStorage(ByteBuf inBuf) {
        byte[] data = new byte[inBuf.readableBytes() + 7 + 4];
        data[0] = 0x78;
        data[1] = 0x01;
        data[2] = 0x01;

        // Write data length
        int length = inBuf.readableBytes();
        data[3] = (byte) length;
        data[4] = (byte) (length >>> 8);
        length = ~length;
        data[5] = (byte) length;
        data[6] = (byte) (length >>> 8);

        // Write data
        inBuf.readBytes(data, 7, inBuf.readableBytes());

        long checksum = adler32(data, 7, data.length - 11);
        data[data.length - 4] = ((byte) ((checksum >> 24) % 256));
        data[data.length - 3] = ((byte) ((checksum >> 16) % 256));
        data[data.length - 2] = ((byte) ((checksum >> 8) % 256));
        data[data.length - 1] = ((byte) (checksum % 256));

        return data;
    }

    /**
     * Calculates the adler32 checksum of the data
     */
    private long adler32(byte[] data, int offset, int length) {
        final Adler32 checksum = new Adler32();
        checksum.update(data, offset, length);
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
