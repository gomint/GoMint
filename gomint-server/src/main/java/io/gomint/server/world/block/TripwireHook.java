package io.gomint.server.world.block;

import io.gomint.world.block.BlockType;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 131 )
public class TripwireHook extends Block implements io.gomint.world.block.BlockTripwireHook {

    @Override
    public int getBlockId() {
        return 131;
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
    public float getBlastResistance() {
        return 0.0f;
    }

    @Override
    public BlockType getType() {
        return BlockType.TRIPWIRE_HOOK;
    }

    @Override
    public boolean canBeBrokenWithHand() {
        return true;
    }

}
