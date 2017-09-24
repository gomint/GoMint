package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 46 )
public class TNT extends Block {

    @Override
    public int getBlockId() {
        return 46;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
