package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemDiamondPickaxe;
import io.gomint.inventory.item.ItemIronPickaxe;
import io.gomint.inventory.item.ItemStack;
import io.gomint.world.block.BlockRedstoneOre;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:redstone_ore" )
public class RedstoneOre extends Block implements BlockRedstoneOre {

    @Override
    public String getBlockId() {
        return "minecraft:redstone_ore";
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.REDSTONE_ORE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemDiamondPickaxe.class,
            ItemIronPickaxe.class
        };
    }

}
