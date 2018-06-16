package io.gomint.server.network.packet;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;
import lombok.Data;

/**
 * @author geNAZt
 * @version 1.0
 */
@Data
public class PacketAddItemEntity extends Packet {

    private long entityId;
    private ItemStack itemStack;
    private float x;
    private float y;
    private float z;
    private float motionX;
    private float motionY;
    private float motionZ;
    private boolean unknown;    // TODO: Maybe fishing flag?

    public PacketAddItemEntity() {
        super( Protocol.PACKET_ADD_ITEM_ENTITY );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        // PE has a system of runtime entity ids
        buffer.writeSignedVarLong( this.entityId );
        buffer.writeUnsignedVarLong( this.entityId );

        // Write the item for the drop
        writeItemStack( this.itemStack, buffer );

        // Write position
        buffer.writeLFloat( this.x );
        buffer.writeLFloat( this.y );
        buffer.writeLFloat( this.z );

        // Write motion
        buffer.writeLFloat( this.motionX );
        buffer.writeLFloat( this.motionY );
        buffer.writeLFloat( this.motionZ );

        // Write unknown flag
        buffer.writeBoolean( this.unknown );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {

    }

}
