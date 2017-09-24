package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 20 )
public class Glass extends Block {

    @Override
    public int getBlockId() {
        return 20;
    }

    @Override
    public long getBreakTime() {
        return 450;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
