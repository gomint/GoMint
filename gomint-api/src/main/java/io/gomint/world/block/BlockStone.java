/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 2
 */
public interface BlockStone extends Block {

    enum Type {
        STONE,
        STONE_SMOOTH,
        GRANITE_SMOOTH,
        DIORITE_SMOOTH,
        GRANITE,
        DIORITE,
        ANDESITE_SMOOTH,
        ANDESITE,
    }

    /**
     * Set type of stone
     *
     * @param type which should be set
     */
    void setStoneType(Type type);

    /**
     * Get the type of stone
     *
     * @return type of stone
     */
    Type getStoneType();

}
