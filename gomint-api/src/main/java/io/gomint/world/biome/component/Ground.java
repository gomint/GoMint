/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.biome.component;

import io.gomint.world.block.Block;

import java.util.List;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 1
 */
public interface Ground {

    /**
     * List of blocks used to fill the ground gap given by min() and max()
     *
     * @return list of blocks to fill the gap, needs to be exactly max() - min() in size()
     */
    List<Block> blocks();

    /**
     * Start, on the y axis, of the ground level
     *
     * @return y coord of the start of this ground
     */
    int min();

    /**
     * End, on the y axis, of the ground level
     *
     * @return y coord of the end of this ground
     */
    int max();

}
