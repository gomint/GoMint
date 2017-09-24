package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketRemoveBlock extends Packet {

    private BlockPosition position;

    public PacketRemoveBlock() {
        super( (byte) -1 );
    }

    @Override
    public void serialize( PacketBuffer buffer ) {
        buffer.writeSignedVarInt( (int) this.position.getX() );
        buffer.writeUnsignedVarInt( (int) this.position.getY() );
        buffer.writeSignedVarInt( (int) this.position.getZ() );
    }

    @Override
    public void deserialize( PacketBuffer buffer ) {
        this.position = readBlockPosition( buffer );
    }

}
