package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:chiseled_nether_bricks" )
public class ChiseledNetherBrick extends Block {

    @Override
    public String getBlockId() {
        return "minecraft:chiseled_nether_brick";
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public BlockType blockType() {
        return BlockType.CHISELED_NETHER_BRICK;
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
