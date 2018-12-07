/*
 * Copyright (c) 2018 Gomint team
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
public class PacketNetworkChunkPublisherUpdate extends Packet {

    private BlockPosition blockPosition;
    private int radius;

    /**
     * Construct a new packet
     */
    public PacketNetworkChunkPublisherUpdate() {
        super( Protocol.PACKET_NETWORK_CHUNK_PUBLISHER_UPDATE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) throws Exception {
        writeSignedBlockPosition( this.blockPosition, buffer );
        buffer.writeUnsignedVarInt( this.radius );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) throws Exception {

    }

}
