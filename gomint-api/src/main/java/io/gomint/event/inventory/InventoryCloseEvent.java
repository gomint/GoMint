package io.gomint.event.inventory;

import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.PlayerEvent;
import io.gomint.inventory.Inventory;

public class InventoryCloseEvent extends PlayerEvent {

    private Inventory inventory;

    public InventoryCloseEvent( EntityPlayer player, Inventory inventory ) {
        super( player );
        this.inventory = inventory;
    }

    /**
     * Get the inventory which is closed
     *
     * @return inventory which used
     */
    public Inventory getInventory() {
        return inventory;
    }
}
