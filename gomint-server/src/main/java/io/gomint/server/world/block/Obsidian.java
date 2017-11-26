package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 49 )
public class Obsidian extends Block implements io.gomint.world.block.BlockObsidian {

    @Override
    public int getBlockId() {
        return 49;
    }

    @Override
    public long getBreakTime() {
        return 75000;
    }

    @Override
    public float getBlastResistance() {
        return 6000.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.OBSIDIAN;
    }

}