/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

import java.util.Objects;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class PacketMovePlayer extends Packet {

    private long entityId;
    private float x;
    private float y;
    private float z;
    private float yaw;
    private float headYaw;          // Always equal to yaw; only differs for animals (see PacketEntityMovement)
    private float pitch;
    private MovePlayerMode mode;
    private boolean onGround;
    private long ridingEntityId;
    private int teleportCause;      // Currently i need documentation for values
    private int teleportItemId;
    private long tick;

    public PacketMovePlayer() {
        super( Protocol.PACKET_MOVE_PLAYER );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarLong( this.entityId );
        buffer.writeLFloat( this.x );
        buffer.writeLFloat( this.y );
        buffer.writeLFloat( this.z );
        buffer.writeLFloat( this.pitch );
        buffer.writeLFloat( this.yaw );
        buffer.writeLFloat( this.headYaw );
        buffer.writeByte( this.mode.getId() );
        buffer.writeBoolean( this.onGround );
        buffer.writeUnsignedVarLong( this.ridingEntityId );

        if ( this.mode == MovePlayerMode.TELEPORT ) {
            buffer.writeLInt( this.teleportCause );
            buffer.writeLInt( this.teleportItemId );
        }

        buffer.writeUnsignedVarLong( this.tick );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.entityId = buffer.readUnsignedVarLong();
        this.x = buffer.readLFloat();
        this.y = buffer.readLFloat();
        this.z = buffer.readLFloat();
        this.pitch = buffer.readLFloat();
        this.yaw = buffer.readLFloat();
        this.headYaw = buffer.readLFloat();
        this.mode = MovePlayerMode.fromValue( buffer.readByte() );
        this.onGround = buffer.readBoolean();
        this.ridingEntityId = buffer.readUnsignedVarLong();

        if ( this.mode == MovePlayerMode.TELEPORT ) {
            this.teleportCause = buffer.readLInt();
            this.teleportItemId = buffer.readLInt();
        }

        this.tick = buffer.readUnsignedVarLong();
    }

    public long getTick() {
        return this.tick;
    }

    public void setTick(long tick) {
        this.tick = tick;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getHeadYaw() {
        return this.headYaw;
    }

    public void setHeadYaw(float headYaw) {
        this.headYaw = headYaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public MovePlayerMode getMode() {
        return this.mode;
    }

    public void setMode(MovePlayerMode mode) {
        this.mode = mode;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public long getRidingEntityId() {
        return this.ridingEntityId;
    }

    public void setRidingEntityId(long ridingEntityId) {
        this.ridingEntityId = ridingEntityId;
    }

    public int getTeleportCause() {
        return this.teleportCause;
    }

    public void setTeleportCause(int teleportCause) {
        this.teleportCause = teleportCause;
    }

    public int getTeleportItemId() {
        return this.teleportItemId;
    }

    public void setTeleportItemId(int teleportItemId) {
        this.teleportItemId = teleportItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PacketMovePlayer that = (PacketMovePlayer) o;
        return this.entityId == that.entityId &&
            Float.compare(that.x, this.x) == 0 &&
            Float.compare(that.y, this.y) == 0 &&
            Float.compare(that.z, this.z) == 0 &&
            Float.compare(that.yaw, this.yaw) == 0 &&
            Float.compare(that.headYaw, this.headYaw) == 0 &&
            Float.compare(that.pitch, this.pitch) == 0 &&
                this.onGround == that.onGround &&
                this.ridingEntityId == that.ridingEntityId &&
                this.teleportCause == that.teleportCause &&
                this.teleportItemId == that.teleportItemId &&
                this.mode == that.mode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entityId, this.x, this.y, this.z, this.yaw, this.headYaw, this.pitch, this.mode, this.onGround, this.ridingEntityId, this.teleportCause, this.teleportItemId);
    }

    public enum MovePlayerMode {
        NORMAL( (byte) 0 ),
        RESET( (byte) 1 ),
        TELEPORT( (byte) 2 ),
        PITCH( (byte) 3 );

        private final byte id;

        MovePlayerMode(byte id) {
            this.id = id;
        }

        public static MovePlayerMode fromValue( byte mode ) {
            switch ( mode ) {
                case 0:
                    return NORMAL;
                case 1:
                    return RESET;
                case 2:
                    return TELEPORT;
                case 3:
                    return PITCH;
                default:
                    return NORMAL;
            }
        }

        public byte getId() {
            return this.id;
        }

    }

}
