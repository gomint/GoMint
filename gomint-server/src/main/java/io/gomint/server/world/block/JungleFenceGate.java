package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 185 )
public class JungleFenceGate extends Block implements io.gomint.world.block.BlockJungleFenceGate {

    @Override
    public int getBlockId() {
        return 185;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 15.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.JUNGLE_FENCE_GATE;
    }

}