package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 12 )
public class Sand extends Block implements io.gomint.world.block.BlockSand {

    @Override
    public int getBlockId() {
        return 12;
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[] {
            ItemIronShovel.class,
            ItemWoodenShovel.class,
            ItemDiamondShovel.class,
            ItemGoldenShovel.class,
            ItemStoneShovel.class
        };
    }

    @Override
    public float getBlastResistance() {
        return 2.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.SAND;
    }

}