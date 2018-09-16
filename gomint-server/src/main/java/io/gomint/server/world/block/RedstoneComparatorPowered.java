package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.world.block.helper.ToolPresets;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:powered_comparator" )
public class RedstoneComparatorPowered extends Block implements io.gomint.world.block.BlockRedstoneComparatorPowered {

    @Override
    public String getBlockId() {
        return "minecraft:powered_comparator";
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.REDSTONE_COMPARATOR_POWERED;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }


}
