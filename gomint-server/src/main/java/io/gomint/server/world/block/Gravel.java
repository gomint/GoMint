package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 13 )
public class Gravel extends Block implements io.gomint.world.block.BlockGravel {

    @Override
    public int getBlockId() {
        return 13;
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemWoodenShovel.class,
            ItemIronShovel.class,
            ItemDiamondShovel.class,
            ItemGoldenShovel.class,
            ItemStoneShovel.class
        };
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 3.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.GRAVEL;
    }

}