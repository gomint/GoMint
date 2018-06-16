/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;
import lombok.Data;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketExplode extends Packet {

    private Vector source;
    private float radius;
    private List<Vector> affectedBlocksRelative;

    /**
     * Construct a new packet
     */
    public PacketExplode() {
        super( Protocol.PACKET_EXPLODE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        writeVector( this.source, buffer );
        buffer.writeSignedVarInt( (int) ( this.radius * 32f ) );
        buffer.writeUnsignedVarInt( ( this.affectedBlocksRelative != null ) ? this.affectedBlocksRelative.size() : 0 );
        if ( this.affectedBlocksRelative != null ) {
            for ( Vector vector : this.affectedBlocksRelative ) {
                writeVector( vector, buffer );
            }
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
