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
import io.gomint.world.block.BlockCobblestoneStairs;
import io.gomint.world.block.BlockType;
import io.gomint.world.block.data.CobblestoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:stone_stairs", def = true )
@RegisterInfo( sId = "minecraft:mossy_cobblestone_stairs" )
public class CobblestoneStairs extends Stairs<BlockCobblestoneStairs> implements BlockCobblestoneStairs {

    @Override
    public long breakTime() {
        return 3000;
    }

    @Override
    public float blastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.COBBLESTONE_STAIRS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public BlockCobblestoneStairs type(CobblestoneType type) {
        if ( type == CobblestoneType.NORMAL ) {
            this.blockId("minecraft:stone_stairs");
        } else {
            this.blockId("minecraft:mossy_cobblestone_stairs");
        }

        return this;
    }

    @Override
    public CobblestoneType type() {
        return "minecraft:stone_stairs".equals(this.blockId()) ? CobblestoneType.NORMAL : CobblestoneType.MOSSY;
    }

}
