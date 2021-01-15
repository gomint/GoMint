/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.BlockColor;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockGlazedTerracotta extends BlockFacing<BlockGlazedTerracotta> {

    /**
     * Set the color of this block
     *
     * @param color of this block
     * @return block for chaining
     */
    BlockGlazedTerracotta color(BlockColor color);

    /**
     * Get the color of this block
     *
     * @return color of this block
     */
    BlockColor color();

}
