/*
 * Copyright (c) 2020 Gomint team
 *
 * This code is licensed under the BSD license found in the
 * LICENSE file in the root directory of this source tree.
 */

package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockCobblestone;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.data.CobblestoneType;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:cobblestone", def = true )
@RegisterInfo( sId = "minecraft:mossy_cobblestone" )
public class Cobblestone extends Block implements BlockCobblestone {

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
        return BlockType.COBBLESTONE;
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
    public BlockCobblestone type(CobblestoneType type) {
        if ( type == CobblestoneType.NORMAL ) {
            this.blockId("minecraft:cobblestone");
        } else {
            this.blockId("minecraft:mossy_cobblestone");
        }

        return this;
    }

    @Override
    public CobblestoneType type() {
        return "minecraft:cobblestone".equals(this.blockId()) ? CobblestoneType.NORMAL : CobblestoneType.MOSSY;
    }

}
