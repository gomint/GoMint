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
public interface BlockWoodenDoor extends BlockDoor {

    /**
     * Get the type of wood which this door has
     *
     * @return type of wood for this door
     */
    WoodType getWoodType();

    /**
     * Set a new type of wood for this door
     *
     * @param woodType which should be the new wood for the door
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
