/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory;

import io.gomint.inventory.item.ItemStack;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface Inventory {

    /**
     * Gets the item out of this inventory
     *
     * @param slot The slot which we want to lookup
     * @return The item stack or null if the slot is empty
     */
    ItemStack getItem( int slot );

    /**
     * Set the item into the slot of this inventory
     *
     * @param slot      The slot in which we want to set this item
     * @param itemStack The item which we want to set into that slot
     */
    void setItem( int slot, ItemStack itemStack );

    /**
     * Add a item to the next free slot in this inventory
     *
     * @param itemStack which should be added to the inventory
     * @return true if it was stored, false when not
     */
    boolean addItem( ItemStack itemStack );

    /**
     * Get the size of this inventory
     *
     * @return The size of this inventory
     */
    int size();

    /**
     * Set all items in this inventory to air
     */
    void clear();

    /**
     * Check if the item is in the inventory
     *
     * @param itemStack which checked if a item is in the inventory
     * @return true if the item is in the inventory
     */

    boolean containsItem( ItemStack itemStack );

}
