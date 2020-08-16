package io.gomint.server.network.packet;

import io.gomint.jraknet.PacketBuffer;
import io.gomint.math.Vector;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
public class PacketInteract extends Packet {

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
            if ( buffer.getRemaining() > 0 ) {
                this.position = readVector(buffer);
            } else {
                System.out.println("No position on mouseover interaction");
            }
        }
    }

    public InteractAction getAction() {
        return action;
    }

    public void setAction(InteractAction action) {
        this.action = action;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public enum InteractAction {
        INTERACT( 1 ),
        ATTACK( 2 ),
        LEAVE_VEHICLE( 3 ),
        MOUSEOVER( 4 ),
        OPEN_NPC( 5 ),
        OPEN_INVENTORY( 6 );

        private final byte id;

        InteractAction( int id ) {
            this.id = (byte) id;
        }

        public byte getId() {
            return id;
        }

        public static InteractAction valueOf(byte actionId ) {
            switch ( actionId ) {
                case 1:
                    return INTERACT;
                case 2:
                    return ATTACK;
                case 3:
                    return LEAVE_VEHICLE;
                case 4:
                    return MOUSEOVER;
                case 5:
                    return OPEN_NPC;
                default:
                case 6:
                    return OPEN_INVENTORY;
            }
        }
    }

}
