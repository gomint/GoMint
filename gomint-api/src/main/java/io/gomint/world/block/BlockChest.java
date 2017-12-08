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
public interface BlockChest extends Block {

    /**
     * Get the chests inventory
     *
     * @return inventory of this chest
     */
    Inventory getInventory();

}
