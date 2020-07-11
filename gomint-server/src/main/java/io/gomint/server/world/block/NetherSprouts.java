package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemShears;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:nether_sprouts" )
public class NetherSprouts extends Block implements io.gomint.world.block.BlockNetherSprouts {

    @Override
    public String getBlockId() {
        return "minecraft:nether_sprouts";
    }

    @Override
    public float getBlastResistance() {
        return 0;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.GILDED_BLACKSTONE;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemShears.class
        };
    }
}
