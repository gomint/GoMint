/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.inventory.Inventory;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockChest extends BlockFacing<BlockChest> {

    /**
     * Get the chests inventory
     *
     * @return inventory of this chest
     */
    Inventory<?> inventory();

    /**
     * Set a custom name for this container
     *
     * @param customName which should be used
     * @return block for chaining
     */
    BlockChest customName(String customName);

    /**
     * Get the custom name of this container
     *
     * @return custom name of this container
     */
    String customName();

}
