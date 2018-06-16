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
public class PacketUpdateBlockSynched extends Packet {

    private BlockPosition position;
    private int blockId;
    private int flags;
    private int layer;

    private long entityId;
    private long action;

    public PacketUpdateBlockSynched() {
        super( Protocol.PACKET_UPDATE_BLOCK_SYNCHED );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        writeBlockPosition( this.position, buffer );
        buffer.writeUnsignedVarInt( this.blockId );
        buffer.writeUnsignedVarInt( this.flags );
        buffer.writeUnsignedVarInt( this.layer );

        buffer.writeUnsignedVarLong( this.entityId );
        buffer.writeUnsignedVarLong( this.action );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }
}
