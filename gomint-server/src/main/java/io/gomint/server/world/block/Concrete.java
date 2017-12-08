package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 236 )
public class Concrete extends Block implements io.gomint.world.block.BlockConcrete {

    @Override
    public int getBlockId() {
        return 236;
    }

    @Override
    public long getBreakTime() {
        return 2700;
    }
    @Override
    public float getBlastResistance() {
        return 9.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.CONCRETE;
    }

}