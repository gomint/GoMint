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
import io.gomint.world.block.BlockBlueIce;
import io.gomint.world.block.BlockType;

@RegisterInfo(sId = "minecraft:blue_ice")
public class BlueIce extends Block implements BlockBlueIce {

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public long getBreakTime() {
        return 4200;
    }

    @Override
    public float getBlastResistance() {
        return 2.8f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BLUE_ICE;
    }

}
