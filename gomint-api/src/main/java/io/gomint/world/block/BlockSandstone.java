/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.Sandcolor;
import io.gomint.world.block.data.SandstoneType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockSandstone extends Block {

    /**
     * Set the color of this block
     *
     * @param color of this block
     * @return block for chaining
     */
    BlockSandstone color(Sandcolor color);

    /**
     * Get the color of this block
     *
     * @return color of this block
     */
    Sandcolor color();

    /**
     * Set the type of this block
     *
     * @param type of this block
     * @return block for chaining
     */
    BlockSandstone type(SandstoneType type);

    /**
     * Get the type of this block
     *
     * @return type of this block
     */
    SandstoneType type();
    
}
