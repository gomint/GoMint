/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.math.BlockPosition;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockDragonEgg extends Block {

    /**
     * Teleport this dragon egg to a random location
     *
     * @return block for chaining
     */
    BlockDragonEgg teleport();

    /**
     * Teleport this dragon egg to the given position
     *
     * @param blockPosition where the egg should be teleported to
     * @return block for chaining
     */
    BlockDragonEgg teleport(BlockPosition blockPosition);

}
