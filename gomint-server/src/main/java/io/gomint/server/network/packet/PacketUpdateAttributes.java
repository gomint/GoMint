package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.entity.AttributeInstance;
import io.gomint.server.network.Protocol;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketUpdateAttributes extends Packet {

    private long entityId;
    private List<AttributeInstance> entries;

    public PacketUpdateAttributes() {
        super( Protocol.PACKET_UPDATE_ATTRIBUTES );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarLong( this.entityId );

        if ( this.entries == null ) {
            buffer.writeUnsignedVarInt( 0 );
        } else {
            buffer.writeUnsignedVarInt( this.entries.size() );
            for ( AttributeInstance entry : this.entries ) {
                buffer.writeLFloat( entry.getMinValue() );
                buffer.writeLFloat( entry.getMaxValue() );
                buffer.writeLFloat( entry.getValue() );
                buffer.writeLFloat( entry.getDefaultValue() );
                buffer.writeString( entry.getKey() );
            }
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.entityId = buffer.readUnsignedVarLong();
        int amountOfAttributes = buffer.readUnsignedVarInt();
        for ( int i = 0; i < amountOfAttributes; i++ ) {
            float minValue = buffer.readLFloat();
            float maxValue = buffer.readLFloat();
            float value = buffer.readLFloat();
            float defaultValue = buffer.readLFloat();
            String key = buffer.readString();

            System.out.println( key + ": min(" + minValue + ") max(" + maxValue + ") value(" + value + ") default(" + defaultValue + ")");
        }
    }

    public void addAttributeInstance( AttributeInstance instance ) {
        if ( this.entries == null ) {
            this.entries = new ArrayList<>();
        }

        this.entries.add( instance );
    }

}
