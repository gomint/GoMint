/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.CoralType;

/**
 * @author Kaooot
 * @version 1.0
 * @stability 2
 */
public interface BlockCoralBlock extends Block {

    /**
     * Should this coral be dead?
     *
     * @param dead true when dead, false otherwise
     * @return block for chaining
     */
    BlockCoralBlock dead(boolean dead);

    /**
     * Check if this coral is dead
     *
     * @return true when dead, false otherwise
     */
    boolean dead();

    /**
     * Set coral type
     *
     * @param type of coral
     * @return block for chaining
     */
    BlockCoralBlock type(CoralType type);

    /**
     * Get type of coral
     *
     * @return type of coral
     */
    CoralType type();

}
