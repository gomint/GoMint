package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 82 )
public class Clay extends Block implements io.gomint.world.block.BlockClay {

    @Override
    public int getBlockId() {
        return 82;
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

    @Override
    public float getBlastResistance() {
        return 3.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.CLAY;
    }

}