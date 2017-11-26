package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 159 )
public class StainedHardenedClay extends Block implements io.gomint.world.block.BlockStainedHardenedClay {

    @Override
    public int getBlockId() {
        return 159;
    }

    @Override
    public long getBreakTime() {
        return 1875;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemWoodenPickaxe.class,
            ItemIronPickaxe.class,
            ItemDiamondPickaxe.class,
            ItemGoldenPickaxe.class,
            ItemStonePickaxe.class
        };
    }

    @Override
    public float getBlastResistance() {
        return 0.75f;
    }

    @Override
    public BlockType getType() {
        return BlockType.STAINED_HARDENED_CLAY;
    }

}