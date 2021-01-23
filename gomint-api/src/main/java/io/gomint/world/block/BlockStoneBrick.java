/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.world.block;

import io.gomint.inventory.item.ItemStoneBrick;
import io.gomint.world.block.data.StoneBrickType;

/**
 * @author geNAZt
 * @version 1.0
 * @stability 3
 */
public interface BlockStoneBrick extends Block {

    /**
     * Set the type of stone brick
     *
     * @param type of stone brick
     */
    BlockStoneBrick type(StoneBrickType type);

    /**
     * Get the type of stone brick
     *
     * @return type of stone brick
     */
    StoneBrickType type();

}
