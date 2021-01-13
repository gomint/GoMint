package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemHoe;
import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.world.block.BlockNetherWarpedWart;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:warped_wart_block")
public class NetherWarpedWart extends Block implements BlockNetherWarpedWart {

    @Override
    public String getBlockId() {
        return "minecraft:warped_wart_block";
    }

    @Override
    public boolean transparent() {
        return true;
    }

    @Override
    public boolean solid() {
        return false;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return new Class[]{
            ItemHoe.class
        };
    }

    @Override
    public BlockType blockType() {
        return BlockType.NETHER_WARPED_WART;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }
}
