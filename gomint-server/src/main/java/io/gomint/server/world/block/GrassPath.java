package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 198 )
public class GrassPath extends Block {

    @Override
    public int getBlockId() {
        return 198;
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
