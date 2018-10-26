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
public interface BlockDragonEgg extends Block {

    /**
     * Teleports the Dragon Egg to a random location up to
     * 7 blocks vertically and 15 blocks horizontally
    */
    void teleport();
}
