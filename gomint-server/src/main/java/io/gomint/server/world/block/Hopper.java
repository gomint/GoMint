package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 154 )
public class Hopper extends Block {

    @Override
    public int getBlockId() {
        return 154;
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
