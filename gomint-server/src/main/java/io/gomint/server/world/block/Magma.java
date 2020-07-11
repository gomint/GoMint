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
import io.gomint.world.block.BlockMagma;
import io.gomint.world.block.BlockType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:magma" )
public class Magma extends Block implements BlockMagma {

    @Override
    public String getBlockId() {
        return "minecraft:magma";
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.MAGMA;
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
