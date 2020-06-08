/*
 * Copyright (c) 2018 GoMint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.Facing;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface BlockFacing extends Block {

    /**
     * Set the facing of this block
     *
     * @param facing of this block
     */
    void setFacing( Facing facing );

    /**
     * Get the direction in which this block
     *
     * @return the direction of this block
     */
    Facing getFacing();

}
