/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory;

import io.gomint.entity.Entity;
import io.gomint.inventory.item.ItemStack;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
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
     * Get a collection of all entities currently viewing this inventory
     *
     * @return collection of viewers
     */
    Collection<Entity> getViewers();

    /**
     * Get the type of this inventory
     *
     * @return type of inventory
     */
    InventoryType getInventoryType();

    /**
     * Get the contents of this inventory
     *
     * @return array copy of item stacks, none of which can be null
     */
    ItemStack[] getContents();

    /**
     * Check if the inventory holds an instance of the asked item stack
     *
     * @param itemStack which should be checked against
     * @return true if inventory contains, false if not
     */
    boolean contains( ItemStack itemStack );

    /**
     * Get a stream of items inside this inventory
     *
     * @return stream of items
     */
    Stream<ItemStack> items();

}
