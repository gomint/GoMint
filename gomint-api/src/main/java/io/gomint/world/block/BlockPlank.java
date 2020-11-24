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
public interface BlockPlank extends Block {

    /**
     * Get plank type
     *
     * @return plank type
     */
    LogType getPlankType();

    /**
     * Set the plank type
     *
     * @param logType which should be used in this block
     */
    void setPlankType( LogType logType);

}
