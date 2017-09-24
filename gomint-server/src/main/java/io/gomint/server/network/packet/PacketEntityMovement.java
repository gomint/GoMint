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
 * @author geNAZt
 * @version 2.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class PacketEntityMovement extends Packet {

    private static final float DIVIDOR = 256f / 360f;

    private long entityId;
    private float x;
    private float y;
    private float z;
    private float yaw;
    private float headYaw;
    private float pitch;

    public PacketEntityMovement() {
        super( Protocol.PACKET_ENTITY_MOVEMENT );
    }

    @Override
    public void serialize( PacketBuffer buffer ) {
        buffer.writeUnsignedVarLong( this.entityId );

        buffer.writeLFloat( this.x );
        buffer.writeLFloat( this.y );
        buffer.writeLFloat( this.z );

        buffer.writeByte( (byte) Math.round( this.pitch * DIVIDOR ) );
        buffer.writeByte( (byte) Math.round( this.headYaw * DIVIDOR ) );
        buffer.writeByte( (byte) Math.round( this.yaw * DIVIDOR ) );
    }

    @Override
    public void deserialize( PacketBuffer buffer ) {

    }

}
