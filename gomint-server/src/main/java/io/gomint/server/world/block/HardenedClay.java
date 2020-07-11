package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:hardened_clay" )
public class HardenedClay extends Block implements io.gomint.world.block.BlockHardenedClay {

    @Override
    public String getBlockId() {
        return "minecraft:hardened_clay";
    }

    @Override
    public long getBreakTime() {
        return 1875;
    }

    @Override
    public float getBlastResistance() {
        return 7.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.HARDENED_CLAY;
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
