/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.QuartzType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockQuartzBlock extends Block, BlockAxis<BlockQuartzBlock> {

    /**
     * Get the variant of this quartz block
     *
     * @return variant of this block
     */
    QuartzType type();

    /**
     * Set the variant of this block
     *
     * @param variant which should be used
     * @return block for chaining
     */
    BlockQuartzBlock type(QuartzType variant);

}
