package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 11 )
public class StationaryLava extends Liquid {

    @Override
    public int getBlockId() {
        return 11;
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

    @Override
    public float getFillHeight() {
        return 1f;
    }
    
}
