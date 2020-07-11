package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:activator_rail" )
public class ActivatorRail extends Block implements io.gomint.world.block.BlockActivatorRail {

    @Override
    public String getBlockId() {
        return "minecraft:activator_rail";
    }

    @Override
    public long getBreakTime() {
        return 1050;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 3.5f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.ACTIVATOR_RAIL;
    }

    @Override
    public Class<? extends ItemStack>[] getToolInterfaces() {
        return ToolPresets.PICKAXE;
    }
}
