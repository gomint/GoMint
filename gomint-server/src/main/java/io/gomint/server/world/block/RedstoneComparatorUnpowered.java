package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 149 )
public class RedstoneComparatorUnpowered extends Block implements io.gomint.world.block.BlockRedstoneComparatorUnpowered {

    @Override
    public int getBlockId() {
        return 149;
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
        return BlockType.REDSTONE_COMPARATOR_UNPOWERED;
    }

}