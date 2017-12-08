package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 163 )
public class AcaciaWoodStairs extends Stairs {

    @Override
    public int getBlockId() {
        return 163;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemWoodenAxe.class,
            ItemIronAxe.class,
            ItemDiamondAxe.class,
            ItemGoldenAxe.class,
            ItemStoneAxe.class
        };
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.ACACIA_WOOD_STAIRS;
    }

}