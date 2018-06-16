package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;
import io.gomint.server.world.SoundMagicNumbers;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketWorldSoundEvent extends Packet {

    private SoundMagicNumbers type;
    private Vector position;
    private int extraData = -1;
    private int pitch = -1;
    private boolean isBabyMob;
    private boolean disableRelativeVolume;

    public PacketWorldSoundEvent() {
        super( Protocol.PACKET_WORLD_SOUND_EVENT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeByte( this.type.getSoundId() );
        buffer.writeLFloat( this.position.getX() );
        buffer.writeLFloat( this.position.getY() );
        buffer.writeLFloat( this.position.getZ() );
        buffer.writeSignedVarInt( this.extraData );
        buffer.writeSignedVarInt( this.pitch );
        buffer.writeBoolean( this.isBabyMob );
        buffer.writeBoolean( this.disableRelativeVolume );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.type = SoundMagicNumbers.valueOf( buffer.readByte() );
        this.position = new Vector( buffer.readLFloat(), buffer.readLFloat(), buffer.readLFloat() );
        this.extraData = buffer.readSignedVarInt();
        this.pitch = buffer.readSignedVarInt();
        this.isBabyMob = buffer.readBoolean();
        this.disableRelativeVolume = buffer.readBoolean();
    }

}
