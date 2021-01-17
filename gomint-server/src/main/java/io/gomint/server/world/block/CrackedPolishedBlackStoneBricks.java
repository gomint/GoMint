package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockCrackedPolishedBlackStoneBricks;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:cracked_polished_blackstone_bricks" )
public class CrackedPolishedBlackStoneBricks extends Block implements BlockCrackedPolishedBlackStoneBricks {

    @Override
    public String blockId() {
        return "minecraft:cracked_polished_blackstone_bricks";
    }

    @Override
    public long breakTime() {
        return 2300;
    }

    @Override
    public float blastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CRACKED_POLISHED_BLACKSTONE_BRICKS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public Class<? extends ItemStack<?>>[] toolInterfaces() {
        return ToolPresets.PICKAXE;
    }
}
