package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 93 )
public class RedstoneRepeaterInactive extends Block {

    @Override
    public int getBlockId() {
        return 93;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
