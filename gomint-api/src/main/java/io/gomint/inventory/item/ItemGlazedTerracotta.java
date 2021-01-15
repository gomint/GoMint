/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.BlockColor;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 1
 */
public interface ItemGlazedTerracotta extends ItemStack<ItemGlazedTerracotta> {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemGlazedTerracotta create(int amount ) {
        return GoMint.instance().createItemStack( ItemGlazedTerracotta.class, amount );
    }

    /**
     * Set the color of this dye
     *
     * @param color of dye
     * @return item for chaining
     */
    ItemGlazedTerracotta color(BlockColor color);

    /**
     * Get the color of dye
     *
     * @return color of dye
     */
    BlockColor color();

}
