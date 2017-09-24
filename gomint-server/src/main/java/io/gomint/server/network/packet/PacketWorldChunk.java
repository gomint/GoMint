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
    private byte[] data;

    public PacketWorldChunk() {
        super( Protocol.PACKET_WORLD_CHUNK );
    }

    @Override
    public void serialize( PacketBuffer buffer ) {
        buffer.writeSignedVarInt( this.x );
        buffer.writeSignedVarInt( this.z );
        buffer.writeUnsignedVarInt( this.data.length );
        buffer.writeBytes( data );
    }

    @Override
    public void deserialize( PacketBuffer buffer ) {

    }

    @Override
    public String toString() {
        return "PacketWorldChunk(x=" + x + ";z=" + z + ";dataHash=" + hashPayload( data ) + ")";
    }

    private long hashPayload( byte[] array ) {
        long h = 0;

        for ( byte aPayload : array ) {
            h = 31 * h + aPayload;
        }

        return h;
    }

}
