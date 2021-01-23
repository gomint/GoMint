/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.Sandcolor;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemSandstoneWall extends ItemStack<ItemSandstoneWall> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     * @return freshly generated item
     */
    static ItemSandstoneWall create(int amount) {
        return GoMint.instance().createItemStack(ItemSandstoneWall.class, amount);
    }

    /**
     * Set the color of this item
     *
     * @param color of this item
     * @return item for chaining
     */
    ItemSandstoneWall color(Sandcolor color);

    /**
     * Get the color of this item
     *
     * @return color of this item
     */
    Sandcolor color();

}
