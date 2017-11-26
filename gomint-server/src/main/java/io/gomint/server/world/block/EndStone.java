package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 121 )
public class EndStone extends Block implements io.gomint.world.block.BlockEndStone {

    @Override
    public int getBlockId() {
        return 121;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public float getBlastResistance() {
        return 45.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.END_STONE;
    }

}