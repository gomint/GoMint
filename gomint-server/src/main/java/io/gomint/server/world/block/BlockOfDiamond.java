package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 57 )
public class BlockOfDiamond extends Block implements io.gomint.world.block.BlockBlockOfDiamond {

    @Override
    public int getBlockId() {
        return 57;
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BLOCK_OF_DIAMOND;
    }

}