package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketSetDifficulty extends Packet {

    private int difficulty;

    public PacketSetDifficulty() {
        super( Protocol.PACKET_SET_DIFFICULTY );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarInt( this.difficulty );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
