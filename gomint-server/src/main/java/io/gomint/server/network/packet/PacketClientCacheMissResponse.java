package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import lombok.Data;

import java.nio.ByteBuffer;
import java.util.function.Consumer;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketClientCacheMissResponse extends Packet {

    private Long2ObjectMap<ByteBuf> data;

    public PacketClientCacheMissResponse() {
        super( Protocol.PACKET_CLIENT_CACHE_MISS_RESPONSE );
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        buffer.writeUnsignedVarInt(this.data.size());
        this.data.long2ObjectEntrySet().forEach(entry -> {
            buffer.writeLLong(entry.getLongKey());
            buffer.writeUnsignedVarInt(entry.getValue().readableBytes());
            buffer.writeBytes(entry.getValue());
            entry.getValue().release();
        });
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {

    }
}
