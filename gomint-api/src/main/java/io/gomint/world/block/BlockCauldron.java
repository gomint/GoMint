/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.LiquidType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockCauldron extends Block {

    /**
     * Get type of liquid for this cauldron
     *
     * @return type of liquid
     */
    LiquidType type();

    /**
     * Set the liquid type of this cauldron
     *
     * @param type of liquid for this cauldron
     * @return block for chaining
     */
    BlockCauldron type(LiquidType type);

    /**
     * Get the percentage of how high the fluid has been in this block
     *
     * @return a value between 0 and 1 where 1 is the whole block full of the given liquid
     */
    float fillHeight();

    /**
     * Set the fill height of the fluid inside this block. Allowed values range from 0 to 1. Other values
     * will be silently ignored
     *
     * @param height of the fluid
     * @return block for chaining
     */
    BlockCauldron fillHeight(float height);

}
