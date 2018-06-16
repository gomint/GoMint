package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 122 )
public class DragonEgg extends Block implements io.gomint.world.block.BlockDragonEgg {

    @Override
    public int getBlockId() {
        return 122;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public float getBlastResistance() {
        return 45.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.DRAGON_EGG;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
