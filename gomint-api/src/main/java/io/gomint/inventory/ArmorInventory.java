/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory;

import io.gomint.inventory.item.ItemStack;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ArmorInventory extends Inventory<ArmorInventory> {

    /**
     * Set the helmet of the inventory
     *
     * @param item which should be used
     */
    ArmorInventory helmet(ItemStack<?> item );

    /**
     * Set the chest plate of the inventory
     *
     * @param item which should be used
     */
    ArmorInventory chestplate(ItemStack<?> item );

    /**
     * Set the leggings of the inventory
     *
     * @param item which should be used
     */
    ArmorInventory leggings(ItemStack<?> item );

    /**
     * Set the boots of the inventory
     *
     * @param item which should be used
     */
    ArmorInventory boots(ItemStack<?> item );

    /**
     * Get the helmet in this inventory
     *
     * @return helmet
     */
    ItemStack<?> helmet();

    /**
     * Get the chest plate in this inventory
     *
     * @return chest plate
     */
    ItemStack<?> chestplate();

    /**
     * Get the leggings in this inventory
     *
     * @return leggings
     */
    ItemStack<?> leggings();

    /**
     * Get the boots in this inventory
     *
     * @return boots
     */
    ItemStack<?> boots();

}
