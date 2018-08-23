package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author lukeeey
 * @version 1.0
 */
@Data
public class PacketPlaySound extends Packet {

    private String soundName;
    private BlockPosition position;
    private float volume;
    private float pitch;

    public PacketPlaySound() {
        super( Protocol.PACKET_PLAY_SOUND );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeString( this.soundName );
        writeBlockPosition( this.position, buffer );
        buffer.writeFloat( this.volume );
        buffer.writeFloat( this.pitch );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.soundName = buffer.readString();
        this.position = readBlockPosition( buffer );
        this.volume = buffer.readFloat();
        this.pitch = buffer.readFloat();
    }

}
