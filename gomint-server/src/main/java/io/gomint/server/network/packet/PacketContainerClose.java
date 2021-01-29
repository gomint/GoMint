package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketContainerClose extends Packet {

    private byte windowId;
    private boolean serverSided;

    /**
     * Construct new container close packet
     */
    public PacketContainerClose() {
        super( Protocol.PACKET_CONTAINER_CLOSE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeByte( this.windowId );
        buffer.writeBoolean( this.serverSided );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.windowId = buffer.readByte();
        this.serverSided = buffer.readBoolean();
    }

    public byte getWindowId() {
        return this.windowId;
    }

    public void setWindowId(byte windowId) {
        this.windowId = windowId;
    }

    public boolean isServerSided() {
        return this.serverSided;
    }

    public void setServerSided(boolean serverSided) {
        this.serverSided = serverSided;
    }
}
