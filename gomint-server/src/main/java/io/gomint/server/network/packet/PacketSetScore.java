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
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
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
        buffer.writeUShort( this.entries.size() );

        for ( ScoreEntry entry : this.entries ) {
            buffer.writeUUID( entry.uuid );
            buffer.writeString( entry.objective );
            buffer.writeLInt( entry.score );
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    @RequiredArgsConstructor
    @Getter
    public static class ScoreEntry {
        private final UUID uuid;
        private final String objective;
        private final int score;
    }

}
