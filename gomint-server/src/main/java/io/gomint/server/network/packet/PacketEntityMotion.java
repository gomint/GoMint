/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;

/**
 * @author BlackyPaw
 * @version 1.0
 */
public class PacketEntityMotion extends Packet {

    private long entityId;
    private Vector velocity;

    public PacketEntityMotion() {
        super( Protocol.PACKET_ENTITY_MOTION );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarLong( this.entityId );
        buffer.writeLFloat( this.velocity.x() );
        buffer.writeLFloat( this.velocity.y() );
        buffer.writeLFloat( this.velocity.z() );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }
}
