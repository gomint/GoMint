package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemDiamondPickaxe;
import io.gomint.inventory.item.ItemIronPickaxe;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:gold_ore" )
public class GoldOre extends Block implements io.gomint.world.block.BlockGoldOre {

    @Override
    public String getBlockId() {
        return "minecraft:gold_ore";
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GOLD_ORE;
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
