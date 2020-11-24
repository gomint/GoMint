/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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

import java.util.Collection;

/**
 * @author BlackyPaw
 * @version 1.0
 */
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

        buffer.writeString( this.entityType.getPersistantId() );

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

    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getVelocityZ() {
        return velocityZ;
    }

    public void setVelocityZ(float velocityZ) {
        this.velocityZ = velocityZ;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getHeadYaw() {
        return headYaw;
    }

    public void setHeadYaw(float headYaw) {
        this.headYaw = headYaw;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public Collection<AttributeInstance> getAttributes() {
        return attributes;
    }

    public void setAttributes(Collection<AttributeInstance> attributes) {
        this.attributes = attributes;
    }

    public MetadataContainer getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataContainer metadata) {
        this.metadata = metadata;
    }
}
