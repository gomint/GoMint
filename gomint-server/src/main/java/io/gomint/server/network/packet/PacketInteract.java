package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;
import lombok.Data;
import lombok.Getter;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketInteract extends Packet {

    public enum InteractAction {
        INTERACT(1),
        ATTACK(2),
        LEAVE_VEHICLE(3),
        MOUSEOVER(4);

        @Getter
        private final byte id;
        InteractAction( int id ) {
            this.id = (byte) id;
        }

        public static InteractAction valueOf( byte actionId ) {
            switch ( actionId ) {
                case 1:
                    return INTERACT;
                case 2:
                    return ATTACK;
                case 3:
                    return LEAVE_VEHICLE;
                case 4:
                    return MOUSEOVER;
                default:
                    return MOUSEOVER;
            }
        }
    }

    private InteractAction action;
    private long entityId;
    private Vector position;

    public PacketInteract() {
        super( Protocol.PACKET_INTERACT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeByte( this.action.getId() );
        buffer.writeUnsignedVarLong( this.entityId );

        if ( this.action == InteractAction.MOUSEOVER ) {
            writeVector( this.position, buffer );
        }
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.action = InteractAction.valueOf( buffer.readByte() );
        this.entityId = buffer.readUnsignedVarLong();

        if ( this.action == InteractAction.MOUSEOVER ) {
            this.position = readVector( buffer );
        }
    }

}
