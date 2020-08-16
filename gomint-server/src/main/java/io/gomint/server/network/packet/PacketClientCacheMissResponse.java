package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;

/**
 * @author geNAZt
 * @version 1.0
 */
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

    public Long2ObjectMap<ByteBuf> getData() {
        return data;
    }

    public void setData(Long2ObjectMap<ByteBuf> data) {
        this.data = data;
    }
}
