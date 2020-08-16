package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketAnimate extends Packet {

    private PlayerAnimation playerAnimation;

    private int actionId;
    private long entityId;
    private float boatRowingTime;

    public PacketAnimate() {
        super( Protocol.PACKET_ANIMATE );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeSignedVarInt( this.actionId );
        buffer.writeUnsignedVarLong( this.entityId );
        if ( (actionId & 0x80) != 0 ) {
            buffer.writeLFloat( this.boatRowingTime );
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.actionId = buffer.readSignedVarInt();
        this.playerAnimation = PlayerAnimation.getById( actionId );
        if ( (actionId & 0x80) != 0 ) {
            this.boatRowingTime = buffer.readLFloat();
        }

        switch ( playerAnimation ) {
            case SWING:
                this.entityId = buffer.readUnsignedVarLong();
                break;
        }
    }

    public enum PlayerAnimation {

        SWING( 1 ),
        BED_WAKEUP( 3 ),
        CRITICAL_HIT( 4 ),
        MAGICAL_CRITICAL_HIT( 5 ),
        ROW_RIGHT( 128 ),
        ROW_LEFT( 129 );

        private int id;

        PlayerAnimation( int id ) {
            this.id = id;
        }

        public static PlayerAnimation getById( int id ) {
            switch ( id ) {
                case 1:
                    return SWING;
                case 3:
                    return BED_WAKEUP;
                case 4:
                    return CRITICAL_HIT;
                case 5:
                    return MAGICAL_CRITICAL_HIT;
                case 128:
                    return ROW_RIGHT;
                case 129:
                    return ROW_LEFT;
                default:
                    return null;
            }
        }

        public int getId() {
            return this.id;
        }
    }

    public PlayerAnimation getPlayerAnimation() {
        return playerAnimation;
    }

    public void setPlayerAnimation(PlayerAnimation playerAnimation) {
        this.playerAnimation = playerAnimation;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public float getBoatRowingTime() {
        return boatRowingTime;
    }

    public void setBoatRowingTime(float boatRowingTime) {
        this.boatRowingTime = boatRowingTime;
    }
}
