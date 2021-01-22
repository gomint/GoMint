/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.Sandcolor;
import io.gomint.world.block.data.SandstoneSlabType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemSandstoneSlab extends ItemSlab<ItemSandstoneSlab> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
	 * @return freshly generated item
     */
    static ItemSandstoneSlab create(int amount ) {
        return GoMint.instance().createItemStack( ItemSandstoneSlab.class, amount );
    }

    /**
     * Set the color of this item
     *
     * @param color of this item
     * @return item for chaining
     */
    ItemSandstoneSlab color(Sandcolor color);

    /**
     * Get the color of this item
     *
     * @return color of this item
     */
    Sandcolor color();

    /**
     * Set the type of this item
     *
     * @param type of this item
     * @return item for chaining
     */
    ItemSandstoneSlab type(SandstoneSlabType type);

    /**
     * Get the type of this item
     *
     * @return type of this item
     */
    SandstoneSlabType type();

}
