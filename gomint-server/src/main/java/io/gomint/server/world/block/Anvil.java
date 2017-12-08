package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 145 )
public class Anvil extends Block implements io.gomint.world.block.BlockAnvil {

    @Override
    public int getBlockId() {
        return 145;
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

    @Override
    public boolean isTransparent() {
        return true;
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
        return 6000.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.ANVIL;
    }

}