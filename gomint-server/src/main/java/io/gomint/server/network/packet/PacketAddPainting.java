package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author HerryYT
 * @version 1.0
 */
@Data
public class PacketAddPainting extends Packet {

    private long entityId;
    private float x;
    private float y;
    private float z;
    private int direction;
    private String title;

    public PacketAddPainting() {
        super( Protocol.PACKET_ADD_PAINTING );
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        // Write runtime & entity id
        buffer.writeSignedVarLong( this.entityId );
        buffer.writeUnsignedVarLong( this.entityId );

        // Write painting position
        buffer.writeLFloat( this.x );
        buffer.writeLFloat( this.y );
        buffer.writeLFloat( this.z );

        // Write painting direction
        buffer.writeSignedVarInt( this.direction );

        // Write painting title
        buffer.writeString( this.title );
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {

    }
}
