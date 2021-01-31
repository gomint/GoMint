package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
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
        this.hideDisconnectionScreen = buffer.readBoolean();
        this.message = buffer.readString();
    }

    public boolean isHideDisconnectionScreen() {
        return this.hideDisconnectionScreen;
    }

    public void setHideDisconnectionScreen(boolean hideDisconnectionScreen) {
        this.hideDisconnectionScreen = hideDisconnectionScreen;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
