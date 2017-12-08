package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 233 )
public class GreenGlazedTerracotta extends Block implements io.gomint.world.block.BlockGreenGlazedTerracotta {

    @Override
    public int getBlockId() {
        return 233;
    }

    @Override
    public long getBreakTime() {
        return 2100;
    }

    @Override
    public float getBlastResistance() {
        return 7.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.GREEN_GLAZED_TERRACOTTA;
    }

}