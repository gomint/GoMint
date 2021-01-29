package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 */
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

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public List<ScoreboardIdentity> getEntries() {
        return this.entries;
    }

    public void setEntries(List<ScoreboardIdentity> entries) {
        this.entries = entries;
    }

    public static class ScoreboardIdentity {
        private final long scoreId;
        private final long entityId;

        public ScoreboardIdentity(long scoreId, long entityId) {
            this.scoreId = scoreId;
            this.entityId = entityId;
        }

        public long getScoreId() {
            return this.scoreId;
        }

        public long getEntityId() {
            return this.entityId;
        }
    }

}
