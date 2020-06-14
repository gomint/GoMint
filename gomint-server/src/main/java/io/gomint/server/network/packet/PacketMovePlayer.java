/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.gomint.server.network.packet.PacketPlayState.PlayState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

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
    private MovePlayerMode mode;
    private boolean onGround;
    private long ridingEntityId;
    private int teleportCause;      // Currently i need documentation for values
    private int teleportItemId;

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
    }

    public enum MovePlayerMode {
        NORMAL( (byte) 0 ),
        RESET( (byte) 1 ),
        TELEPORT( (byte) 2 ),
        PITCH( (byte) 3 );

        @Getter
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
    }

}
