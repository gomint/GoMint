/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
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
        readSignedBlockPosition(buffer);
        buffer.readUnsignedVarInt();
    }

    public BlockPosition getBlockPosition() {
        return blockPosition;
    }

    public void setBlockPosition(BlockPosition blockPosition) {
        this.blockPosition = blockPosition;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
