/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.inventory.item.data.SandType;

/**
 * @author geNAZt
 * @version 1.0
 */
public interface BlockSand extends Block {

    /**
     * Set type of sand
     *
     * @param type of sand
     */
    void setType(SandType type);

    /**
     * Get type of sand
     *
     * @return type of sand
     */
    SandType getType();

}
