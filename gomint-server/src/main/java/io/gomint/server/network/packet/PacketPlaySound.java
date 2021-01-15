package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;

/**
 * Packet sent whenever a specific sound should be played to a player.
 *
 * @author Ciel DeVille
 * @version 1.0
 * @stability 1
 */
public class PacketPlaySound extends Packet {

    private String sound;
    private Vector position;
    private float volume;
    private float pitch;

    public PacketPlaySound() {
        super(Protocol.PACKET_PLAY_SOUND);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) throws Exception {
        buffer.writeString(this.sound);
        buffer.writeLFloat(this.position.getX());
        buffer.writeLFloat(this.position.getY());
        buffer.writeLFloat(this.position.getZ());
        buffer.writeLFloat(this.volume);
        buffer.writeLFloat(this.pitch);
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) throws Exception {
        this.sound = buffer.readString();
        this.position = new Vector(buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat());
        this.volume = buffer.readLFloat();
        this.pitch = buffer.readLFloat();
    }

    @Override
    public String toString() {
        return "PacketPlaySound{" +
                "sound=" + this.sound +
                ", position={" + this.position.getX() + ", " + this.position.getY() + ", " + this.position.getZ() +
                "}, volume=" + this.volume +
                ", pitch=" + this.pitch +
                "}";
    }
}
