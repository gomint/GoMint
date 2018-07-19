/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.entity.AttributeInstance;
import io.gomint.server.entity.EntityType;
import io.gomint.server.entity.metadata.MetadataContainer;
import io.gomint.server.network.Protocol;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class PacketSpawnEntity extends Packet {

    private long entityId;
    private EntityType entityType;
    private float x;
    private float y;
    private float z;
    private float velocityX;
    private float velocityY;
    private float velocityZ;
    private float pitch;
    private float headYaw;
    private float yaw;
    private Collection<AttributeInstance> attributes;
    private MetadataContainer metadata;

    public PacketSpawnEntity() {
        super( Protocol.PACKET_SPAWN_ENTITY );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarLong( this.entityId );
        buffer.writeUnsignedVarLong( this.entityId );
        buffer.writeUnsignedVarInt( this.entityType.getId() );
        buffer.writeLFloat( this.x );
        buffer.writeLFloat( this.y );
        buffer.writeLFloat( this.z );
        buffer.writeLFloat( this.velocityX );
        buffer.writeLFloat( this.velocityY );
        buffer.writeLFloat( this.velocityZ );
        buffer.writeLFloat( this.pitch );
        buffer.writeLFloat( this.yaw );
        buffer.writeLFloat( this.headYaw );

        if ( this.attributes == null ) {
            buffer.writeUnsignedVarInt( 0 );
        } else {
            buffer.writeUnsignedVarInt( this.attributes.size() );
            for ( AttributeInstance entry : this.attributes ) {
                buffer.writeString( entry.getKey() );
                buffer.writeLFloat( entry.getMinValue() );
                buffer.writeLFloat( entry.getValue() );
                buffer.writeLFloat( entry.getMaxValue() );
            }
        }

        this.metadata.serialize( buffer );
        buffer.writeUnsignedVarInt( 0 );             // Entity links; TODO: implement this
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.entityId = buffer.readSignedVarLong().longValue();
        buffer.readUnsignedVarLong();

        int typeId = buffer.readUnsignedVarInt();
        System.out.println( typeId );
        for ( EntityType type : EntityType.values() ) {
            if ( typeId == type.getId() ) {
                this.entityType = type;
                break;
            }
        }

        buffer.skip( buffer.getRemaining() );
    }

}
