/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.StoneBrickStairsType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockStoneBrickStairs extends BlockStairs<BlockStoneBrickStairs> {

    /**
     * Set the type of stone brick stairs
     *
     * @param type of stone brick stairs
     */
    BlockStoneBrickStairs type(StoneBrickStairsType type);

    /**
     * Get the type of stone brick stairs
     *
     * @return type of stone brick stairs
     */
    StoneBrickStairsType type();

}
