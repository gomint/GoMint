package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:cracked_polished_blackstone_bricks" )
public class CrackedPolishedBlackStoneBricks extends Block implements io.gomint.world.block.BlockCrackedPolishedBlackStoneBricks {

    @Override
    public String getBlockId() {
        return "minecraft:cracked_polished_blackstone_bricks";
    }

    @Override
    public long getBreakTime() {
        return 2300;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.CRACKED_POLISHED_BLACKSTONE_BRICKS;
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
