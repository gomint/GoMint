package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketSetCommandsEnabled extends Packet {
    private boolean enabled;

    public PacketSetCommandsEnabled() {
        super( Protocol.PACKET_SET_COMMANDS_ENABLED );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeBoolean( this.enabled );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
