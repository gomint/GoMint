package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemDiamondPickaxe;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockCryingObsidian;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:crying_obsidian")
public class CryingObsidian extends Block implements BlockCryingObsidian {

    @Override
    public String getBlockId() {
        return "minecraft:crying_obsidian";
    }

    @Override
    public long breakTime() {
        return 75200;
    }

    @Override
    public Class<? extends ItemStack<?>>[] getToolInterfaces() {
        return new Class[]{
            ItemDiamondPickaxe.class
        };
    }

    @Override
    public float getBlastResistance() {
        return 6000.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CRYING_OBSIDIAN;
    }
}
