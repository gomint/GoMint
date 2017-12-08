package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.entity.Entity;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 65 )
public class Ladder extends Block implements io.gomint.world.block.BlockLadder {

    @Override
    public int getBlockId() {
        return 65;
    }

    @Override
    public long getBreakTime() {
        return 600;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public void stepOn( Entity entity ) {
        // Reset fall distance
        entity.resetFallDistance();
    }

    @Override
    public float getBlastResistance() {
        return 2.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.LADDER;
    }

}