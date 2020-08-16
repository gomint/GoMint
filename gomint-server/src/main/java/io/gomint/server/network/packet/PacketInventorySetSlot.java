package io.gomint.server.network.packet;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 * @version 1.0
 */
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
        writeItemStackWithID( this.itemStack, buffer );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.windowId = buffer.readUnsignedVarInt();
        this.slot = buffer.readUnsignedVarInt();
        this.itemStack = readItemStackWithID( buffer );
    }

    public int getWindowId() {
        return windowId;
    }

    public void setWindowId(int windowId) {
        this.windowId = windowId;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
