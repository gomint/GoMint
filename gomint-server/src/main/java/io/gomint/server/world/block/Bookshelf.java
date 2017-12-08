package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 47 )
public class Bookshelf extends Block implements io.gomint.world.block.BlockBookshelf {

    @Override
    public int getBlockId() {
        return 47;
    }

    @Override
    public long getBreakTime() {
        return 2250;
    }

    @Override
    public float getBlastResistance() {
        return 7.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.BOOKSHELF;
    }

}