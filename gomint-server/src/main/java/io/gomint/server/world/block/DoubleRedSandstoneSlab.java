package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 181 )
public class DoubleRedSandstoneSlab extends Block implements io.gomint.world.block.BlockDoubleRedSandstoneSlab {

    @Override
    public int getBlockId() {
        return 181;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.DOUBLE_RED_SANDSTONE_SLAB;
    }

}