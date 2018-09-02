/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
@ToString
public class PacketSetScore extends Packet {

    private byte type;
    private List<ScoreEntry> entries;

    /**
     * Construct a new packet
     */
    public PacketSetScore() {
        super( Protocol.PACKET_SET_SCORE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeByte( this.type );
        buffer.writeUnsignedVarInt( this.entries.size() );

        for ( ScoreEntry entry : this.entries ) {
            buffer.writeSignedVarLong( entry.scoreId );
            buffer.writeString( entry.objective );
            buffer.writeLInt( entry.score );

            if ( this.type == 0 ) {
                buffer.writeByte( entry.entityType );
                switch ( entry.entityType ) {
                    case 3: // Fake entity
                        buffer.writeString( entry.fakeEntity );
                        break;
                    case 1:
                    case 2:
                        buffer.writeUnsignedVarLong( entry.entityId );
                        break;
                }
            }
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    @Getter
    @ToString
    public static class ScoreEntry {
        private final long scoreId;
        private final String objective;
        private final int score;

        // Add entity type
        private byte entityType;
        private String fakeEntity;
        private long entityId;
    }

}
