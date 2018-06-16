package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketAnimate extends Packet {

    private PlayerAnimation playerAnimation;

    private int actionId;
    private long entityId;

    public PacketAnimate() {
        super(Protocol.PACKET_ANIMATE);
    }

    @Override
    public void serialize(PacketBuffer buffer, int protocolID) {
        buffer.writeSignedVarInt(this.actionId);
        buffer.writeUnsignedVarLong(this.entityId);
    }

    @Override
    public void deserialize(PacketBuffer buffer, int protocolID) {
        this.actionId = buffer.readSignedVarInt();
        this.playerAnimation = PlayerAnimation.getById( actionId );
        switch ( playerAnimation ){
            case SWING:
                this.entityId = buffer.readUnsignedVarLong();
                break;
        }
    }

    public enum PlayerAnimation {

        SWING( 1 );

        private int id;

        PlayerAnimation( int id ) { this.id = id; }

        public static PlayerAnimation getById( int id ) {
            switch ( id ) {
                case 1:
                    return SWING;
                default:
                    return null;
            }
        }

        public int getId() {
            return this.id;
        }
    }
}
