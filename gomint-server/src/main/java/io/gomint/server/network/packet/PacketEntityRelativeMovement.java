/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketEntityRelativeMovement extends Packet {

    private long entityId;
    private short flags;

    private float oldX;
    private float oldY;
    private float oldZ;

    private float x;
    private float y;
    private float z;

    private float oldPitch;
    private float oldYaw;
    private float oldHeadYaw;

    private float pitch;
    private float yaw;
    private float headYaw;

    /**
     * Construct a new packet
     */
    public PacketEntityRelativeMovement() {
        super( Protocol.PACKET_ENTITY_RELATIVE_MOVEMENT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarLong( this.entityId );

        short flags = 0;
        if ( this.x != this.oldX ) {
            flags |= 1;
        }

        if ( this.y != this.oldY ) {
            flags |= 2;
        }

        if ( this.z != this.oldZ ) {
            flags |= 4;
        }

        if ( this.pitch != this.oldPitch ) {
            flags |= 8;
        }

        if ( this.headYaw != this.oldHeadYaw ) {
            flags |= 16;
        }

        if ( this.yaw != this.oldYaw ) {
            flags |= 32;
        }

        buffer.writeLShort( flags );

        if ( this.x != this.oldX ) {
            buffer.writeLFloat( this.x );
        }

        if ( this.y != this.oldY ) {
            buffer.writeLFloat( this.y );
        }

        if ( this.z != this.oldZ ) {
            buffer.writeLFloat( this.z );
        }

        if ( this.pitch != this.oldPitch ) {
            writeByteRotation( this.pitch, buffer );
        }

        if ( this.headYaw != this.oldHeadYaw ) {
            writeByteRotation( this.headYaw, buffer );
        }

        if ( this.yaw != this.oldYaw ) {
            writeByteRotation( this.yaw, buffer );
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.entityId = buffer.readUnsignedVarLong();
        this.flags = buffer.readLShort();

        if ( ( this.flags & 1 ) == 1 ) {
            this.x = buffer.readLFloat();
        }

        if ( ( this.flags & 2 ) == 2 ) {
            this.y = buffer.readLFloat();
        }

        if ( ( this.flags & 4 ) == 4 ) {
            this.z = buffer.readLFloat();
        }

        if ( ( this.flags & 8 ) == 8 ) {
            this.pitch = readByteRotation(buffer);
        }

        if ( ( this.flags & 16 ) == 16 ) {
            this.headYaw = readByteRotation(buffer);
        }

        if ( ( this.flags & 32 ) == 32 ) {
            this.yaw = readByteRotation(buffer);
        }
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }

    public float getOldX() {
        return oldX;
    }

    public void setOldX(float oldX) {
        this.oldX = oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public void setOldY(float oldY) {
        this.oldY = oldY;
    }

    public float getOldZ() {
        return oldZ;
    }

    public void setOldZ(float oldZ) {
        this.oldZ = oldZ;
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

    public float getOldPitch() {
        return oldPitch;
    }

    public void setOldPitch(float oldPitch) {
        this.oldPitch = oldPitch;
    }

    public float getOldYaw() {
        return oldYaw;
    }

    public void setOldYaw(float oldYaw) {
        this.oldYaw = oldYaw;
    }

    public float getOldHeadYaw() {
        return oldHeadYaw;
    }

    public void setOldHeadYaw(float oldHeadYaw) {
        this.oldHeadYaw = oldHeadYaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getHeadYaw() {
        return headYaw;
    }

    public void setHeadYaw(float headYaw) {
        this.headYaw = headYaw;
    }

}
