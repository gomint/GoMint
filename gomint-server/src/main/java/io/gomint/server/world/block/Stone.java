package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 1 )
public class Stone extends Block implements io.gomint.world.block.BlockStone {

    @Override
    public int getBlockId() {
        return 1;
    }

    @Override
    public long getBreakTime() {
        return 2250;
    }

    @Override
    public float getBlastResistance() {
        return 10.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.STONE;
    }

}