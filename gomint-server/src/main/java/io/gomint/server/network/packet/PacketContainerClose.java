package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketContainerClose extends Packet {

    private byte windowId;

    /**
     * Construct new container close packet
     */
    public PacketContainerClose() {
        super( Protocol.PACKET_CONTAINER_CLOSE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeByte( this.windowId );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.windowId = buffer.readByte();
    }

    public byte getWindowId() {
        return windowId;
    }

    public void setWindowId(byte windowId) {
        this.windowId = windowId;
    }
}
