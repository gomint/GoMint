/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketRespawnPosition extends Packet {

    private Vector position;

    public PacketRespawnPosition() {
        super( Protocol.PACKET_RESPAWN_POSITION );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        writeVector( this.position, buffer );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }
}
