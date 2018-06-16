/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class PacketMovePlayer extends Packet {

    private long entityId;
    private float x;
    private float y;
    private float z;
    private float yaw;
    private float headYaw;          // Always equal to yaw; only differs for animals (see PacketEntityMovement)
    private float pitch;
    private byte mode;
    private boolean onGround;
    private long ridingEntityId;

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
        buffer.writeLFloat( this.headYaw );
        buffer.writeLFloat( this.yaw );
        buffer.writeByte( this.mode );
        buffer.writeBoolean( this.onGround );
        buffer.writeUnsignedVarLong( this.ridingEntityId );

        if ( this.mode == 2 ) {
            buffer.writeLInt( 0 );
            buffer.writeLInt( 0 );
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.entityId = buffer.readUnsignedVarLong();
        this.x = buffer.readLFloat();
        this.y = buffer.readLFloat();
        this.z = buffer.readLFloat();
        this.pitch = buffer.readLFloat();
        this.headYaw = buffer.readLFloat();
        this.yaw = buffer.readLFloat();
        this.mode = buffer.readByte();
        this.onGround = buffer.readBoolean();
        this.ridingEntityId = buffer.readUnsignedVarLong();
    }

}
