package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 198 )
public class GrassPath extends Block implements io.gomint.world.block.BlockGrassPath {

    @Override
    public int getBlockId() {
        return 198;
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 3.25f;
    }

    @Override
    public BlockType getType() {
        return BlockType.GRASS_PATH;
    }

}