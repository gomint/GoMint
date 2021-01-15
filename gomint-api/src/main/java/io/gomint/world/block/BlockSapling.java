/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.LogType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockSapling extends Block {

    /**
     * Set the type of sapling
     *
     * @param type of sapling
     * @return block for chaining
     */
    BlockSapling type(LogType type);

    /**
     * Get the type of this sapling
     *
     * @return type of sapling
     */
    LogType type();


}
