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
public class PacketEntityFall extends Packet {

    private long entityId;
    private float fallDistance;
    private boolean inVoid;

    /**
     * Construct a new packet
     */
    public PacketEntityFall() {
        super( Protocol.PACKET_ENTITY_FALL );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {

    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.entityId = buffer.readUnsignedVarLong();
        this.fallDistance = buffer.readLFloat();
        this.inVoid = buffer.readBoolean();
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public float getFallDistance() {
        return fallDistance;
    }

    public void setFallDistance(float fallDistance) {
        this.fallDistance = fallDistance;
    }

    public boolean isInVoid() {
        return inVoid;
    }

    public void setInVoid(boolean inVoid) {
        this.inVoid = inVoid;
    }
}
