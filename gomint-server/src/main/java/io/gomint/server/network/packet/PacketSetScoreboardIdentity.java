package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketSetScoreboardIdentity extends Packet {

    private byte type;
    private List<ScoreboardIdentity> entries;

    /**
     * Construct a new packet
     */
    protected PacketSetScoreboardIdentity() {
        super( Protocol.PACKET_SET_SCOREBOARD_IDENTITY );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeByte( this.type );

        for ( ScoreboardIdentity entry : this.entries ) {
            buffer.writeUnsignedVarLong( entry.scoreId );

            if ( this.type == 0 ) {
                buffer.writeUnsignedVarLong( entry.entityId );
            }
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

    @RequiredArgsConstructor
    @Getter
    public static class ScoreboardIdentity {
        private final long scoreId;
        private long entityId;
    }

}
