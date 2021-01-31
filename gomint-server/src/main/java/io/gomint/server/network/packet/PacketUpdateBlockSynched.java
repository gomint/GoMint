/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
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

    public BlockPosition getPosition() {
        return this.position;
    }

    public void setPosition(BlockPosition position) {
        this.position = position;
    }

    public int getBlockId() {
        return this.blockId;
    }

    public void setBlockId(int blockId) {
        this.blockId = blockId;
    }

    public int getFlags() {
        return this.flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getLayer() {
        return this.layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public long getAction() {
        return this.action;
    }

    public void setAction(long action) {
        this.action = action;
    }
}
