/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockCartographyTable;
import io.gomint.world.block.BlockSmithingTable;
import io.gomint.world.block.BlockType;

@RegisterInfo(sId = "minecraft:cartography_table")
public class CartographyTable extends Block implements BlockCartographyTable {

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.AXE;
    }

    @Override
    public long breakTime() {
        return 3750;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CARTOGRAPHY_TABLE;
    }

    @Override
    public float blastResistance() {
        return 12.5f;
    }

}
