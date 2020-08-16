package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author HerryYT
 * @version 1.0
 */
public class PacketRiderJump extends Packet {

    private int strength;

    public PacketRiderJump() {
        super( Protocol.PACKET_RIDER_JUMP );
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        buffer.writeSignedVarInt( this.strength );
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {
        this.strength = buffer.readSignedVarInt();
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
