package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketPlayerAction extends Packet {

    private long entityId;

    private PlayerAction action;
    private BlockPosition position;

    private int face;

    // There is more data but who knows what that could be

    public PacketPlayerAction() {
        super( Protocol.PACKET_PLAYER_ACTION );
    }

    @Override
    public void serialize( PacketBuffer buffer ) {

    }

    @Override
    public void deserialize( PacketBuffer buffer ) {
        this.entityId = buffer.readUnsignedVarLong();
        this.action = PlayerAction.valueOf( buffer.readSignedVarInt() );
        this.position = readBlockPosition( buffer );
        this.face = buffer.readSignedVarInt();
    }

    public enum PlayerAction {
        START_BREAK,
        ABORT_BREAK,
        STOP_BREAK,
        CONTINUE_BREAK,

        RELEASE_ITEM,
        STOP_SLEEPING,
        RESPAWN,
        JUMP,

        START_SPRINT,
        STOP_SPRINT,
        START_SNEAK,
        STOP_SNEAK,

        DIMENSION_CHANGE,
        ABORT_DIMENSION_CHANGE,

        INTERACT_BLOCK;

        public static PlayerAction valueOf( int actionId ) {
            switch ( actionId ) {
                case 0:
                    return START_BREAK;
                case 1:
                    return ABORT_BREAK;
                case 2:
                    return STOP_BREAK;
                case 5:
                    return RELEASE_ITEM;
                case 6:
                    return STOP_SLEEPING;
                case 7:
                    return RESPAWN;
                case 8:
                    return JUMP;
                case 9:
                    return START_SPRINT;
                case 10:
                    return STOP_SPRINT;
                case 11:
                    return START_SNEAK;
                case 12:
                    return STOP_SNEAK;
                case 13:
                    return DIMENSION_CHANGE;
                case 14:
                    return ABORT_DIMENSION_CHANGE;
                case 17:
                    return INTERACT_BLOCK;
                case 18:
                    return CONTINUE_BREAK;
            }

            System.out.println( "Unknown action id: " + actionId );
            return null;
        }
    }

}
