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

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketEntityRelativeMovement extends Packet {

    private long entityId;
    private int flags;

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

        byte flags = 0;
        if ( this.x != 0 ) {
            flags |= 1;
        }

        if ( this.y != 0 ) {
            flags |= 2;
        }

        if ( this.z != 0 ) {
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

        buffer.writeByte( flags );

        if ( this.x != 0 ) {
            buffer.writeSignedVarInt( Float.floatToIntBits( this.x ) - Float.floatToIntBits( this.oldX ) );
        }

        if ( this.y != 0 ) {
            buffer.writeSignedVarInt( Float.floatToIntBits( this.y ) - Float.floatToIntBits( this.oldY ) );
        }

        if ( this.z != 0 ) {
            buffer.writeSignedVarInt( Float.floatToIntBits( this.z ) - Float.floatToIntBits( this.oldZ ) );
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

    }

}
