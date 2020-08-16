package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author HerryYT
 * @version 1.0
 */
public class PacketTickSync extends Packet {

    private long clientTiming;
    private long serverTiming;

    public PacketTickSync() {
        super(Protocol.PACKET_TICK_SYNC );
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        buffer.writeLLong( this.clientTiming );
        buffer.writeLLong( this.serverTiming );
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {
        this.clientTiming = buffer.readLLong();
        this.serverTiming = buffer.readLLong();
    }

    public long getClientTiming() {
        return clientTiming;
    }

    public void setClientTiming(long clientTiming) {
        this.clientTiming = clientTiming;
    }

    public long getServerTiming() {
        return serverTiming;
    }

    public void setServerTiming(long serverTiming) {
        this.serverTiming = serverTiming;
    }
}
