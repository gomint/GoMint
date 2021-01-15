package io.gomint.server.inventory;

import io.gomint.inventory.InventoryType;
import io.gomint.server.inventory.item.Items;
import io.gomint.server.network.PlayerConnection;
import io.gomint.server.network.packet.PacketInventoryContent;
import io.gomint.server.network.packet.PacketInventorySetSlot;

/**
 * @author geNAZt
 * @version 1.0
 */
public class CursorInventory extends Inventory<CursorInventory> {

    public CursorInventory(Items items, InventoryHolder owner) {
        super(items, owner, 1);
    }

    @Override
    public void sendContents(int slot, PlayerConnection playerConnection) {
        PacketInventorySetSlot setSlot = new PacketInventorySetSlot();
        setSlot.setSlot(slot);
        setSlot.setWindowId(WindowMagicNumbers.CURSOR_DEPRECATED);
        setSlot.setItemStack(this.contents[slot]);
        playerConnection.addToSendQueue(setSlot);
    }

    @Override
    public void sendContents(PlayerConnection playerConnection) {
        PacketInventoryContent inventory = new PacketInventoryContent();
        inventory.setWindowId(WindowMagicNumbers.CURSOR_DEPRECATED);
        inventory.setItems(contents());
        playerConnection.addToSendQueue(inventory);
    }

    @Override
    public InventoryType inventoryType() {
        return InventoryType.CURSOR;
    }

}
