package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 246 )
public class GlowingObsidian extends Block implements io.gomint.world.block.BlockGlowingObsidian {

    @Override
    public int getBlockId() {
        return 246;
    }

    @Override
    public float getBlastResistance() {
        return 6000.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.GLOWING_OBSIDIAN;
    }

}