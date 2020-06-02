package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.gomint.server.resource.ResourcePack;
import lombok.Data;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketResourcePackStack extends Packet {

    private boolean mustAccept;
    private List<ResourcePack> behaviourPackEntries;
    private List<ResourcePack> resourcePackEntries;

    private boolean experimental;
    private String gameVersion = Protocol.MINECRAFT_PE_NETWORK_VERSION;

    public PacketResourcePackStack() {
        super( Protocol.PACKET_RESOURCEPACK_STACK );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeBoolean( this.mustAccept );

        buffer.writeUnsignedVarInt( ( this.behaviourPackEntries == null ? 0 : this.behaviourPackEntries.size() ) );
        if ( this.behaviourPackEntries != null ) {
            for ( ResourcePack entry : this.behaviourPackEntries ) {
                buffer.writeString( entry.getVersion().getId().toString() );
                buffer.writeString( entry.getVersion().getVersion() );
                buffer.writeString( "" );
            }
        }

        buffer.writeUnsignedVarInt( (short) ( this.resourcePackEntries == null ? 0 : this.resourcePackEntries.size() ) );
        if ( this.resourcePackEntries != null ) {
            for ( ResourcePack entry : this.resourcePackEntries ) {
                buffer.writeString( entry.getVersion().getId().toString() );
                buffer.writeString( entry.getVersion().getVersion() );
                buffer.writeString( "" );
            }
        }

        buffer.writeBoolean( this.experimental );
        buffer.writeString( this.gameVersion );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        buffer.readBoolean();

        int amount = buffer.readUnsignedVarInt();
        for ( int i = 0; i < amount; i++ ) {
            buffer.readString();
            buffer.readString();
            buffer.readString();
        }

        amount = buffer.readUnsignedVarInt();
        for ( int i = 0; i < amount; i++ ) {
            buffer.readString();
            buffer.readString();
            buffer.readString();
        }

        this.experimental = buffer.readBoolean();
        this.gameVersion = buffer.readString();
    }

}
