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
public interface BlockWoodenStair extends BlockStair {

    /**
     * Get the type of wood from which this button has been made
     *
     * @return type of wood
     */
    LogType getWoodType();

    /**
     * Set the type of wood for this button
     *
     * @param logType type of wood
     */
    void setWoodType(LogType logType);

}
