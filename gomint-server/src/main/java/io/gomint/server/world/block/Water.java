package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 8 )
public class Water extends Liquid {

    @Override
    public int getBlockId() {
        return 8;
    }

    @Override
    public long getBreakTime() {
        return 150000;
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
