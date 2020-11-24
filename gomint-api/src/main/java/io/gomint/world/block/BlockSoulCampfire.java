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
 * @stability 3
 */
public interface BlockSoulCampfire extends Block, BlockDirection {

    /**
     * Set extinguished state for this campfire
     *
     * @param value true when extinguished, false otherwise
     */
    void setExtinguished(boolean value);

    /**
     * Check if this campfire is extinguished or not
     *
     * @return true if extinguished, false otherwise
     */
    boolean isExtinguished();

}
