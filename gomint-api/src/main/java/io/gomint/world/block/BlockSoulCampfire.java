/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

/**
 * @author KingAli
 * @version 1.0
 * @stability 1
 */
public interface BlockSoulCampfire extends Block, BlockDirection<BlockSoulCampfire> {

    /**
     * Set extinguished state for this campfire
     *
     * @param value true when extinguished, false otherwise
     * @return block for chaining
     */
    BlockSoulCampfire extinguished(boolean value);

    /**
     * Check if this campfire is extinguished or not
     *
     * @return true if extinguished, false otherwise
     */
    boolean isExtinguished();

}
