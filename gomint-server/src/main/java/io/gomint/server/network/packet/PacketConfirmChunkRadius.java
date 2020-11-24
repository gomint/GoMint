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
public class PacketConfirmChunkRadius extends Packet {

    private int chunkRadius;

    public PacketConfirmChunkRadius() {
        super( Protocol.PACKET_CONFIRM_CHUNK_RADIUS );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarInt( this.chunkRadius );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.chunkRadius = buffer.readSignedVarInt();
    }

    public int getChunkRadius() {
        return chunkRadius;
    }

    public void setChunkRadius(int chunkRadius) {
        this.chunkRadius = chunkRadius;
    }

}
