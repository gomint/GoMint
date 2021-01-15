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
public interface BlockWoodenDoor extends BlockDoor<BlockWoodenDoor> {

    /**
     * Get the type of wood which this door has
     *
     * @return type of wood for this door
     */
    LogType type();

    /**
     * Set a new type of wood for this door
     *
     * @param logType which should be the new wood for the door
     * @return block for chaining
     */
    BlockWoodenDoor type(LogType logType);

}
