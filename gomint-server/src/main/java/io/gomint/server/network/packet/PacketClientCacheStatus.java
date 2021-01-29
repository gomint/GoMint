package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author HerryYT
 * @version 1.0
 */
public class PacketClientCacheStatus extends Packet {

    private boolean enabled;

    public PacketClientCacheStatus() {
        super( Protocol.PACKET_CLIENT_CACHE_STATUS );
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        buffer.writeBoolean( this.enabled );
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {
        this.enabled = buffer.readBoolean();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
