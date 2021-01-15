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
public interface BlockWoodenButton extends BlockButton<BlockWoodenButton> {

    /**
     * Get the type of wood from which this button has been made
     *
     * @return type of wood
     */
    LogType type();

    /**
     * Set the type of wood for this button
     *
     * @param logType type of wood
     * @return block for chaining
     */
    BlockWoodenButton type(LogType logType);

}
