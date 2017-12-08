package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 78 )
public class SnowLayer extends Block implements io.gomint.world.block.BlockSnowLayer {

    @Override
    public int getBlockId() {
        return 78;
    }

    @Override
    public long getBreakTime() {
        return 300;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 0.5f;
    }

    @Override
    public BlockType getType() {
        return BlockType.SNOW_LAYER;
    }

}