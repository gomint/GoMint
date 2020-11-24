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
public interface BlockWoodenSlab extends BlockSlab {

    /**
     * Get the type of wood
     *
     * @return type of wood
     */
    LogType getWoodType();

    /**
     * Set the type of wood
     *
     * @param logType for this block
     */
    void setWoodType( LogType logType);

}
