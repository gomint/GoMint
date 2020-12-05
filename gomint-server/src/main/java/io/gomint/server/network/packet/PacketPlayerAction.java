package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.BlockPosition;
import io.gomint.server.network.Protocol;
import io.gomint.world.block.data.Facing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketPlayerAction extends Packet {

    private static final Logger LOGGER = LoggerFactory.getLogger(PacketPlayerAction.class);

    private long entityId;

    private PlayerAction action;
    private BlockPosition position;

    private Facing face;

    // There is more data but who knows what that could be

    public PacketPlayerAction() {
        super( Protocol.PACKET_PLAYER_ACTION );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {

    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.entityId = buffer.readUnsignedVarLong();
        this.action = PlayerAction.valueOf( buffer.readSignedVarInt() );
        this.position = readBlockPosition( buffer );
        this.face = readBlockFace( buffer );
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public PlayerAction getAction() {
        return action;
    }

    public void setAction(PlayerAction action) {
        this.action = action;
    }

    public BlockPosition getPosition() {
        return position;
    }

    public void setPosition(BlockPosition position) {
        this.position = position;
    }

    public Facing getFace() {
        return face;
    }

    public void setFace(Facing face) {
        this.face = face;
    }

    @Override
    public String toString() {
        return "PacketPlayerAction{" +
            "entityId=" + entityId +
            ", action=" + action +
            ", position=" + position +
            ", face=" + face +
            '}';
    }

    public enum PlayerAction {
        START_BREAK,
        ABORT_BREAK,
        STOP_BREAK,
        GET_UPDATED_BLOCK,

        RELEASE_ITEM,
        START_SLEEPING,
        STOP_SLEEPING,
        RESPAWN,
        JUMP,

        START_SPRINT,
        STOP_SPRINT,
        START_SNEAK,
        STOP_SNEAK,

        DIMENSION_CHANGE_REQUEST,
        DIMENSION_CHANGE_ACK,

        START_GLIDE,
        STOP_GLIDE,

        BUILD_DENIED,

        CONTINUE_BREAK,

        CHANGE_SKIN,

        SET_ENCHANT_SEED,

        START_SWIMMING,
        STOP_SWIMMING,

        START_SPIN_ATTACK,
        STOP_SPIN_ATTACK,

        INTERACT_BLOCK;

        public static PlayerAction valueOf( int actionId ) {
            switch ( actionId ) {
                case 0:
                    return START_BREAK;
                case 1:
                    return ABORT_BREAK;
                case 2:
                    return STOP_BREAK;
                case 3:
                    return GET_UPDATED_BLOCK;
                case 4:
                    return RELEASE_ITEM;
                case 5:
                    return START_SLEEPING;
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
                    return DIMENSION_CHANGE_REQUEST;
                case 14:
                    return DIMENSION_CHANGE_ACK;
                case 15:
                    return START_GLIDE;
                case 16:
                    return STOP_GLIDE;
                case 17:
                    return BUILD_DENIED;
                case 18:
                    return CONTINUE_BREAK;
                case 19:
                    return CHANGE_SKIN;
                case 20:
                    return SET_ENCHANT_SEED;
                case 21:
                    return START_SWIMMING;
                case 22:
                    return STOP_SWIMMING;
                case 23:
                    return START_SPIN_ATTACK;
                case 24:
                    return STOP_SPIN_ATTACK;
                case 25:
                    return INTERACT_BLOCK;
            }

            LOGGER.warn( "Unknown action id: {}", actionId );
            return null;
        }
    }

}
