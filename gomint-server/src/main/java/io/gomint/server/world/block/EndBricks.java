package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockEndBricks;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:end_bricks" )
public class EndBricks extends Block implements BlockEndBricks {

    @Override
    public String getBlockId() {
        return "minecraft:end_bricks";
    }

    @Override
    public float getBlastResistance() {
        return 4.0f;
    }

    @Override
    public BlockType blockType() {
        return BlockType.END_BRICKS;
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
