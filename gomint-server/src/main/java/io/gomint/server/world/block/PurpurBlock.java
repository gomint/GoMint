package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:purpur_block" )
public class PurpurBlock extends Block implements io.gomint.world.block.BlockPurpurBlock {

    @Override
    public String getBlockId() {
        return "minecraft:purpur_block";
    }

    @Override
    public long getBreakTime() {
        return 2250;
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PURPUR_BLOCK;
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
