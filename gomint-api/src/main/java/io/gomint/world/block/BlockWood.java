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
     * Set the type of wood
     *
     * @param type of wood
     */
    void setWoodType( BlockWood.Type type );

    /**
     * Get the type of this wood
     *
     * @return type of wood
     */
    BlockWood.Type getWoodType();

    enum Type {
        OAK,
        SPRUCE,
        BIRCH,
        JUNGLE,
        ACACIA,
        DARK_OAK
    }
}
