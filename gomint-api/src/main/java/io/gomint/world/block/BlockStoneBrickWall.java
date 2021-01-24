/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.StoneBrickSlabType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockStoneBrickWall extends Block {

    /**
     * Get the type of stone this slab has
     *
     * @return type of stone
     */
    StoneBrickSlabType type();

    /**
     * Set the type of stone for this slab
     *
     * @param type which this slab should have
     */
    BlockStoneBrickWall type(StoneBrickSlabType type);

}
