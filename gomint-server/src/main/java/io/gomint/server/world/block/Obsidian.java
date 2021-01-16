package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemDiamondPickaxe;
import io.gomint.inventory.item.ItemStack;
import io.gomint.world.block.BlockObsidian;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:obsidian" )
public class Obsidian extends Block implements BlockObsidian {

    @Override
    public String getBlockId() {
        return "minecraft:obsidian";
    }

    @Override
    public long breakTime() {
        return 75000;
    }

    @Override
    public float getBlastResistance() {
        return 6000.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.OBSIDIAN;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return new Class[]{
            ItemDiamondPickaxe.class
        };
    }

}
