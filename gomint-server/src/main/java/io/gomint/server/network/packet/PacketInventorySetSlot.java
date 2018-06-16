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
public class PacketInventorySetSlot extends Packet {

    private int windowId;
    private int slot;
    private ItemStack itemStack;

    public PacketInventorySetSlot() {
        super( Protocol.PACKET_INVENTORY_SET_SLOT );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarInt( this.windowId );
        buffer.writeUnsignedVarInt( this.slot );
        writeItemStack( this.itemStack, buffer );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.windowId = buffer.readUnsignedVarInt();
        this.slot = buffer.readUnsignedVarInt();
        this.itemStack = readItemStack( buffer );
    }

}
