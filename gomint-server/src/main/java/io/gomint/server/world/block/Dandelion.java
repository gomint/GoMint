package io.gomint.server.world.block;

import io.gomint.world.block.BlockDandelion;
import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:yellow_flower" )
public class Dandelion extends Block implements BlockDandelion {

    @Override
    public String getBlockId() {
        return "minecraft:yellow_flower";
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
    public long getBreakTime() {
        return 0;
    }

    @Override
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DANDELION;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
