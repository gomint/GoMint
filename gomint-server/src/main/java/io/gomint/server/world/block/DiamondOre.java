package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 56 )
public class DiamondOre extends Block implements io.gomint.world.block.BlockDiamondOre {

    @Override
    public int getBlockId() {
        return 56;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.DIAMOND_ORE;
    }

}