package io.gomint.server.world.block;

import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.inventory.item.*;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:sandstone_stairs" )
public class SandstoneStairs extends Stairs {

    @Override
    public String getBlockId() {
        return "minecraft:sandstone_stairs";
    }

    @Override
    public long getBreakTime() {
        return 1200;
    }

    @Override
    public float getBlastResistance() {
        return 4.0f;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }

    @Override
    public BlockType getType() {
        return BlockType.SANDSTONE_STAIRS;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
