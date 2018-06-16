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

    private static final byte FLAG_ON_GROUND = 0x1;
    private static final byte FLAG_TELEPORTED = 0x2;

    private long entityId;
    private float x;
    private float y;
    private float z;
    private float yaw;
    private float headYaw;
    private float pitch;
    private boolean onGround;
    private boolean teleported;

    public PacketEntityMovement() {
        super( Protocol.PACKET_ENTITY_MOVEMENT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarLong( this.entityId );

        // TODO: PRTCL 274
        if ( protocolID == Protocol.MINECRAFT_PE_BETA_PROTOCOL_VERSION ) {
            byte flags = this.onGround ? FLAG_ON_GROUND : 0;
            if ( this.teleported ) {
                flags |= FLAG_TELEPORTED;
            }

            buffer.writeByte( flags );
        }

        buffer.writeLFloat( this.x );
        buffer.writeLFloat( this.y );
        buffer.writeLFloat( this.z );

        writeByteRotation( this.pitch, buffer );
        writeByteRotation( this.headYaw, buffer );
        writeByteRotation( this.yaw, buffer );

        // TODO: PRTCL 261
        if ( protocolID == Protocol.MINECRAFT_PE_PROTOCOL_VERSION ) {
            buffer.writeBoolean( this.onGround );
            buffer.writeBoolean( this.teleported );
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
