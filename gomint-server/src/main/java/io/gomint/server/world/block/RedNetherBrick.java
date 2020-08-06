/*
 * Copyright (c) 2017, GoMint, BlackyPaw and geNAZt
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockRedNetherBrick;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:red_nether_brick" )
public class RedNetherBrick extends Block implements BlockRedNetherBrick {

    @Override
    public String getBlockId() {
        return "minecraft:red_nether_brick";
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RED_NETHER_BRICK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

}
