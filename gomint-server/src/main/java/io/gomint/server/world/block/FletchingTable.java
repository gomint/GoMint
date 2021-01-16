/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockFletchingTable;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:fletching_table")
public class FletchingTable extends Block implements BlockFletchingTable {

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.FLETCHING_TABLE;
    }

}
