/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory.item;

import io.gomint.world.block.data.PrismarineType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemPrismarineDoubleSlab extends ItemStack<ItemPrismarineDoubleSlab> {

    /**
     * Get the type of prismarine this slab has
     *
     * @return type of prismarine
     */
    PrismarineType type();

    /**
     * Set the type of prismarine for this item
     *
     * @param type for this item
     * @return item for chaining
     */
    ItemPrismarineDoubleSlab type(PrismarineType type);


}
