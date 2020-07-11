package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:warped_nylium" )
public class WarpedNylium extends Block implements io.gomint.world.block.BlockWarpedNylium {

    @Override
    public String getBlockId() {
        return "minecraft:warped_nylium";
    }

    @Override
    public long getBreakTime() {
        return 1500;
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WARPED_NYLIUM;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }
}
