package io.gomint.server.network;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.maintenance.ReportUploader;
import io.gomint.server.network.packet.Packet;
import io.gomint.server.network.packet.PacketBatch;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PostProcessWorker implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostProcessWorker.class);

    private final ConnectionWithState connection;
    private final Packet[] packets;
    private final Consumer<Void> callback;

    public PostProcessWorker(ConnectionWithState connection, Packet[] packets, Consumer<Void> callback) {
        this.connection = connection;
        this.packets = packets;
        this.callback = callback;
    }

    @Override
    public void run() {
        ByteBuf inBuf = writePackets(this.packets);
        if (inBuf.readableBytes() == 0) {
            inBuf.release();
            return;
        }

        PacketBatch batch = new PacketBatch();
        batch.setPayload(this.connection.getOutputProcessor().process(inBuf));
        this.connection.send(batch);

        if (this.callback != null) {
            this.callback.accept(null);
        }
    }

    private ByteBuf writePackets(Packet[] packets) {
        // Write all packets into the inBuf for compression
        PacketBuffer buffer = new PacketBuffer(16);
        ByteBuf inBuf = newNettyBuffer();

        for (Packet packet : packets) {
            buffer.setReadPosition(0);
            buffer.setWritePosition(0);

            // CHECKSTYLE:OFF
            try {
                LOGGER.debug("Writing (batch) packet: {}", packet.getClass().getName());

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

        return inBuf;
    }

    private ByteBuf newNettyBuffer() {
        return PooledByteBufAllocator.DEFAULT.directBuffer();
    }

    private void writeVarInt(int value, ByteBuf stream) {
        while ((value & -128) != 0) {
            stream.writeByte(value & 127 | 128);
            value >>>= 7;
        }

        stream.writeByte(value);
    }

}
