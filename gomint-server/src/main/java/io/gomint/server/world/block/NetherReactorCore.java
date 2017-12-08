package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 247 )
public class NetherReactorCore extends Block implements io.gomint.world.block.BlockNetherReactorCore {

    @Override
    public int getBlockId() {
        return 247;
    }

    @Override
    public float getBlastResistance() {
        return 30.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.NETHER_REACTOR_CORE;
    }

}