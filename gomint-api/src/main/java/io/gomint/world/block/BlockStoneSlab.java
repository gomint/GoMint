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
public interface BlockStoneSlab extends BlockSlab<BlockStoneSlab> {

    /**
     * Get the type of stone this slab has
     *
     * @return type of stone
     */
    StoneType type();

    /**
     * Set the type of stone for this block
     *
     * @param stoneType for this block
     * @return block for chaining
     */
    BlockStoneSlab type(StoneType stoneType);

}
