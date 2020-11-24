/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author BlackyPaw
 * @version 1.0
 */
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
        this.release();
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {
        this.x = buffer.readSignedVarInt();
        this.z = buffer.readSignedVarInt();
        this.subChunkCount = buffer.readUnsignedVarInt();
        buffer.readBoolean();

        int length = buffer.readUnsignedVarInt();
        this.data = PooledByteBufAllocator.DEFAULT.directBuffer(length);

        byte[] data = new byte[length];
        buffer.readBytes(data);
        this.data.writeBytes(data);
    }

    public void release() {
        this.data.release();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public boolean isCached() {
        return cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }

    public long[] getHashes() {
        return hashes;
    }

    public void setHashes(long[] hashes) {
        this.hashes = hashes;
    }

    public int getSubChunkCount() {
        return subChunkCount;
    }

    public void setSubChunkCount(int subChunkCount) {
        this.subChunkCount = subChunkCount;
    }

    public ByteBuf getData() {
        return data;
    }

    public void setData(ByteBuf data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PacketWorldChunk that = (PacketWorldChunk) o;
        return x == that.x &&
            z == that.z &&
            cached == that.cached &&
            subChunkCount == that.subChunkCount &&
            Arrays.equals(hashes, that.hashes) &&
            Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(x, z, cached, subChunkCount, data);
        result = 31 * result + Arrays.hashCode(hashes);
        return result;
    }
}
