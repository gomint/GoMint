package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketDisconnect extends Packet {

    private boolean hideDisconnectionScreen = false;
    private String message;

    public PacketDisconnect() {
        super( Protocol.PACKET_DISCONNECT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeBoolean( this.hideDisconnectionScreen );
        buffer.writeString( this.message );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
