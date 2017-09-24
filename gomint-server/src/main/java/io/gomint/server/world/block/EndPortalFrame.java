package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 120 )
public class EndPortalFrame extends Block {

    @Override
    public int getBlockId() {
        return 120;
    }

    @Override
    public long getBreakTime() {
        return -1;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
