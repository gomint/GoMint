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
public class PacketRemoveObjective extends Packet {

    private String objectiveName;

    /**
     * Create new packet
     */
    public PacketRemoveObjective() {
        super( Protocol.PACKET_REMOVE_OBJECTIVE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeString( this.objectiveName );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }
}
