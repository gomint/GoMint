/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.StoneType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockStoneStairs extends BlockStairs<BlockStoneStairs> {

    /**
     * Get the type of stone from which this stair has been made
     *
     * @return type of stone
     */
    StoneType type();

    /**
     * Set the type of stone for this stair
     *
     * @param stoneType type of stone
     * @return block for chaining
     */
    BlockStoneStairs type(StoneType stoneType);

}