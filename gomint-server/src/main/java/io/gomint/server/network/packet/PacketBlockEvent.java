/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketBlockEvent extends Packet {

    private BlockPosition position;
    private int data1;
    private int data2;

    /**
     * Construct a new packet
     */
    public PacketBlockEvent() {
        super( Protocol.PACKET_BLOCK_EVENT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        writeBlockPosition( this.position, buffer );
        buffer.writeSignedVarInt( this.data1 );
        buffer.writeSignedVarInt( this.data2 );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
