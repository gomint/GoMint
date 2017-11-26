package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 206 )
public class EndBricks extends Block implements io.gomint.world.block.BlockEndBricks {

    @Override
    public int getBlockId() {
        return 206;
    }

    @Override
    public float getBlastResistance() {
        return 4.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.END_BRICKS;
    }

}