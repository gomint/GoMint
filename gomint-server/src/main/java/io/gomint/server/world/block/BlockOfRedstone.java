package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 152 )
public class BlockOfRedstone extends Block implements io.gomint.world.block.BlockBlockOfRedstone {

    @Override
    public int getBlockId() {
        return 152;
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BLOCK_OF_REDSTONE;
    }

}