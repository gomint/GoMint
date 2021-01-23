/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.world.block.data.PrismarineType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockPrismarine extends Block {

    /**
     * Set the type of prismarine
     *
     * @param type of prismarine
     */
    BlockPrismarine type(PrismarineType type);

    /**
     * Get the type of prismarine
     *
     * @return type of prismarine
     */
    PrismarineType type();

}
