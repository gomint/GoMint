package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 30 )
public class Cobweb extends Block {

    @Override
    public int getBlockId() {
        return 30;
    }

    @Override
    public long getBreakTime() {
        return 6000;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
