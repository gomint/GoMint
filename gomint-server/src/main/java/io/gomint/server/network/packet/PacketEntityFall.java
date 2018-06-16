/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Getter
public class PacketEntityFall extends Packet {

    private long entityId;
    private float fallDistance;
    private boolean unknown;

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
        this.unknown = buffer.readBoolean();
    }
}
