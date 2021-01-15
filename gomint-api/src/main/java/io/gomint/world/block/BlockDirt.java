/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.DirtType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 1
 */
public interface BlockDirt extends Block {

    /**
     * Set type of dirt
     *
     * @param type which should be set
     * @return block for chaining
     */
    BlockDirt type(DirtType type);

    /**
     * Get the type of dirt
     *
     * @return type of dirt
     */
    DirtType type();

}
