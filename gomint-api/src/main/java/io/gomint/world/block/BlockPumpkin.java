/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.PumpkinType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockPumpkin extends Block, BlockDirection {

    /**
     * Get the type of pumpkin
     *
     * @return type of pumpkin
     */
    PumpkinType getType();

    /**
     * Set the type of pumpkin
     *
     * @param type of pumpkin
     */
    void setType(PumpkinType type);

}
