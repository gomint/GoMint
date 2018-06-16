package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
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

    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.windowId = buffer.readByte();
    }

}
