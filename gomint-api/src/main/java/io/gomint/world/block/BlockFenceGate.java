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
public interface BlockFenceGate extends BlockDirection {

    /**
     * Toggle this fence gate
     */
    void toggle();

    /**
     * Check if this fence gate is open
     *
     * @return true if open, false if not
     */
    boolean isOpen();

    /**
     * Set the type of wood for this fence gate
     *
     * @param logType type of wood
     */
    void setWoodType( LogType logType );

    /**
     * Get the type of wood from which this fence gate has been made
     *
     * @return type of wood
     */
    LogType getWoodType();

}
