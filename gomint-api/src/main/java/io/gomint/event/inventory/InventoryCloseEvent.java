package io.gomint.event.inventory;

import io.gomint.entity.EntityPlayer;
import io.gomint.event.player.PlayerEvent;
import io.gomint.inventory.Inventory;

/**
 * @author geNAZt
 * @version 1.0
 */
public class InventoryCloseEvent extends PlayerEvent {

    private Inventory inventory;

    /**
     * Create a new inventory close event
     *
     * @param player which closed the inventory
     * @param inventory which has been closed
     */
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
        return this.inventory;
    }

}
