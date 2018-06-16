package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketWorldEvent extends Packet {

    private int eventId;
    private Vector position;
    private int data;

    /**
     * Construct a new packet
     */
    public PacketWorldEvent() {
        super( Protocol.PACKET_WORLD_EVENT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarInt( this.eventId );
        writeVector( this.position, buffer );
        buffer.writeSignedVarInt( this.data );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
