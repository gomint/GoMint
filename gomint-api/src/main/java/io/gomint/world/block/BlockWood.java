/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface BlockWood extends Block {

    /**
     * Get wood type
     *
     * @return wood type
     */
    WoodType getWoodType();

    /**
     * Set the wood type
     *
     * @param woodType which should be used in this block
     */
    void setWoodType( WoodType woodType );

    enum WoodType {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK
    }

}
