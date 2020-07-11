/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PacketWorldChunk extends Packet {

    private int x;
    private int z;

    private boolean cached;
    private long[] hashes;
    private int subChunkCount;

    private ByteBuf data;

    public PacketWorldChunk() {
        super(Protocol.PACKET_WORLD_CHUNK);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        buffer.writeSignedVarInt(this.x);
        buffer.writeSignedVarInt(this.z);
        buffer.writeUnsignedVarInt(this.subChunkCount);
        buffer.writeBoolean(this.cached);

        if (this.cached) {
            buffer.writeUnsignedVarInt(this.hashes.length);
            for (long hash : this.hashes) {
                buffer.writeLLong(hash);
            }
        }

        buffer.writeUnsignedVarInt(this.data.readableBytes());
        buffer.writeBytes(this.data);
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {

    }

}
