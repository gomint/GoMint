package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.gomint.server.resource.ResourcePack;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketResourcePacksInfo extends Packet {

    private boolean mustAccept;
    private boolean hasScripts;
    private List<ResourcePack> behaviourPackEntries;
    private List<ResourcePack> resourcePackEntries;

    public PacketResourcePacksInfo() {
        super( Protocol.PACKET_RESOURCEPACK_INFO );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeBoolean( this.mustAccept );
        buffer.writeBoolean( this.hasScripts );

        buffer.writeLShort( (short) ( this.behaviourPackEntries == null ? 0 : this.behaviourPackEntries.size() ) );
        if ( this.behaviourPackEntries != null ) {
            for ( ResourcePack entry : this.behaviourPackEntries ) {
                buffer.writeString( entry.getVersion().getId().toString() );
                buffer.writeString( entry.getVersion().getVersion() );
                buffer.writeLLong( entry.getSize() );
                buffer.writeString( "" );
                buffer.writeString( "" );
                buffer.writeString( "" );
                buffer.writeBoolean( false );
            }
        }

        buffer.writeLShort( (short) ( this.resourcePackEntries == null ? 0 : this.resourcePackEntries.size() ) );
        if ( this.resourcePackEntries != null ) {
            for ( ResourcePack entry : this.resourcePackEntries ) {
                buffer.writeString( entry.getVersion().getId().toString() );
                buffer.writeString( entry.getVersion().getVersion() );
                buffer.writeLLong( entry.getSize() );
                buffer.writeString( "" );
                buffer.writeString( "" );
                buffer.writeString( "" );
                buffer.writeBoolean( false );
                buffer.writeBoolean( false );
            }
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.mustAccept = buffer.readBoolean();
        this.hasScripts = buffer.readBoolean();

        short behaviourAmount = buffer.readLShort();
        for ( short i = 0; i < behaviourAmount; i++ ) {
            buffer.readString();
            buffer.readString();
            buffer.readLLong();
            buffer.readString();
            buffer.readString();
            buffer.readString();
            buffer.readBoolean();
        }

        behaviourAmount = buffer.readLShort();
        for ( short i = 0; i < behaviourAmount; i++ ) {
            buffer.readString();
            buffer.readString();
            buffer.readLLong();
            buffer.readString();
            buffer.readString();
            buffer.readString();
            buffer.readBoolean();
        }
    }

    public boolean isMustAccept() {
        return mustAccept;
    }

    public void setMustAccept(boolean mustAccept) {
        this.mustAccept = mustAccept;
    }

    public boolean isHasScripts() {
        return hasScripts;
    }

    public void setHasScripts(boolean hasScripts) {
        this.hasScripts = hasScripts;
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
