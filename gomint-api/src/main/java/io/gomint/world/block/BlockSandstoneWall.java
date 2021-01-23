/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.Sandcolor;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockSandstoneWall extends BlockWall<BlockSandstoneWall> {

    /**
     * Set the color of this item
     *
     * @param color of this item
     * @return item for chaining
     */
    BlockSandstoneWall color(Sandcolor color);

    /**
     * Get the color of this item
     *
     * @return color of this item
     */
    Sandcolor color();


}
