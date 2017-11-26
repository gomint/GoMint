package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 155 )
public class BlockOfQuartz extends Block implements io.gomint.world.block.BlockBlockOfQuartz {

    @Override
    public int getBlockId() {
        return 155;
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
    public BlockType getType() {
        return BlockType.BLOCK_OF_QUARTZ;
    }

}