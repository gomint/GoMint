/*
 * Copyright (c) 2020, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.inventory.item;

import io.gomint.GoMint;
import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface ItemDoubleStoneSlab extends ItemStack {

    /**
     * Create a new item stack with given class and amount
     *
     * @param amount which is used for the creation
     */
    static ItemDoubleStoneSlab create( int amount ) {
        return GoMint.instance().createItemStack( ItemDoubleStoneSlab.class, amount );
    }

    /**
     * Get the type of stone this double slab has
     *
     * @return type of stone
     */
    StoneType getStoneType();

    /**
     * Set the type of stone for this double slab
     *
     * @param type which this slab should have
     */
    void setStoneType(StoneType type);

}
