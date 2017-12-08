package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 52 )
public class MobSpawner extends Block implements io.gomint.world.block.BlockMobSpawner {

    @Override
    public int getBlockId() {
        return 52;
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 25.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.MOB_SPAWNER;
    }

}