package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 73 )
public class RedstoneOre extends Block {

    @Override
    public int getBlockId() {
        return 73;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
