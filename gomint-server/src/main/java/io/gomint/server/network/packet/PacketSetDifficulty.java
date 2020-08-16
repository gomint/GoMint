package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
