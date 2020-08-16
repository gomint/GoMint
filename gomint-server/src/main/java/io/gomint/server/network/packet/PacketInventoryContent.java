package io.gomint.server.network.packet;

import io.gomint.inventory.item.ItemStack;
import io.gomint.jraknet.PacketBuffer;
import io.gomint.server.network.Protocol;

/**
 * @author geNAZt
 */
public class PacketInventoryContent extends Packet {

    private int windowId;
    private ItemStack[] items;

    public PacketInventoryContent() {
        super( Protocol.PACKET_INVENTORY_CONTENT_PACKET );
    }

    @Override
    public void serialize( PacketBuffer buffer, int protocolID ) {
        buffer.writeUnsignedVarInt( this.windowId );
        writeItemStacksWithIDs( this.items, buffer );
    }

    @Override
    public void deserialize( PacketBuffer buffer, int protocolID ) {
        this.windowId = buffer.readUnsignedVarInt();
        this.items = readItemStacksWithIDs( buffer );
    }

    public int getWindowId() {
        return windowId;
    }

    public void setWindowId(int windowId) {
        this.windowId = windowId;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }
}
