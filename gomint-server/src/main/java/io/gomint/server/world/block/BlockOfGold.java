package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 41 )
public class BlockOfGold extends Block implements io.gomint.world.block.BlockBlockOfGold {

    @Override
    public int getBlockId() {
        return 41;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BLOCK_OF_GOLD;
    }

}