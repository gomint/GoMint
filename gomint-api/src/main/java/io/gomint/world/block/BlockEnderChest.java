/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.inventory.Inventory;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface BlockEnderChest extends Block {

    /**
     * Get the chests inventory
     *
     * @return inventory of this chest
     */
    Inventory getInventory();

    /**
     * Set a custom name for this container
     *
     * @param customName which should be used
     */
    void setCustomName( String customName );

    /**
     * Get the custom name of this container
     *
     * @return custom name of this container
     */
    String getCustomName();

}
