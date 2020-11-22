package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.gomint.server.resource.ResourcePack;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketResourcePackStack extends Packet {

    private boolean mustAccept;
    private List<ResourcePack> behaviourPackEntries;
    private List<ResourcePack> resourcePackEntries;

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

        buffer.writeString( Protocol.MINECRAFT_PE_NETWORK_VERSION );

        // Experiments
        buffer.writeInt(0);
        buffer.writeBoolean(false);
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

        buffer.readString();

        amount = buffer.readLInt();
        for ( int i = 0; i < amount; i++ ) {
            buffer.readString();
            buffer.readBoolean();
        }

        buffer.readBoolean();
    }

    public boolean isMustAccept() {
        return mustAccept;
    }

    public void setMustAccept(boolean mustAccept) {
        this.mustAccept = mustAccept;
    }

    public List<ResourcePack> getBehaviourPackEntries() {
        return behaviourPackEntries;
    }

    public void setBehaviourPackEntries(List<ResourcePack> behaviourPackEntries) {
        this.behaviourPackEntries = behaviourPackEntries;
    }

    public List<ResourcePack> getResourcePackEntries() {
        return resourcePackEntries;
    }

    public void setResourcePackEntries(List<ResourcePack> resourcePackEntries) {
        this.resourcePackEntries = resourcePackEntries;
    }
}
