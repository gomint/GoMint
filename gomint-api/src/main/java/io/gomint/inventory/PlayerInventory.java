package io.gomint.inventory;

import io.gomint.inventory.item.ItemStack;

/**
 * @author geNAZt
 */
public interface PlayerInventory extends Inventory {

    /**
     * Get the item in hand
     *
     * @return item stack which the client holds in hand
     */
    ItemStack getItemInHand();

    /**
     * Get the index of the item in hand in this inventory
     *
     * @return index of item in hand
     */
    byte getItemInHandSlot();

}
