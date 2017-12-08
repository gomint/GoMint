package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 21 )
public class LapisLazuliOre extends Block implements io.gomint.world.block.BlockLapisLazuliOre {

    @Override
    public int getBlockId() {
        return 21;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public float getBlastResistance() {
        return 5.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.LAPIS_LAZULI_ORE;
    }

}