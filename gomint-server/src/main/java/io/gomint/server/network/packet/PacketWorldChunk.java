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
import lombok.EqualsAndHashCode;

/**
 * @author BlackyPaw
 * @version 1.0
 */
@Data
@EqualsAndHashCode( callSuper = false )
public class PacketWorldChunk extends Packet {

    private int x;
    private int z;

    private boolean cached;
    private int subChunkCount;

    private byte[] data;
    private int dataLength;

    public PacketWorldChunk() {
        super( Protocol.PACKET_WORLD_CHUNK );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarInt( this.x );
        buffer.writeSignedVarInt( this.z );
        buffer.writeUnsignedVarInt( this.subChunkCount );
        buffer.writeBoolean( this.cached );
        buffer.writeUnsignedVarInt( this.dataLength );
        buffer.writeBytes( this.data, 0, this.dataLength );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.x = buffer.readSignedVarInt();
        this.z = buffer.readSignedVarInt();
        this.cached = buffer.readBoolean();
        this.subChunkCount = buffer.readUnsignedVarInt();
        this.dataLength = buffer.readUnsignedVarInt();
        this.data = new byte[this.dataLength];
        buffer.readBytes( this.data );
    }

}
