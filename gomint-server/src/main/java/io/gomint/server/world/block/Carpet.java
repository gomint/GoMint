package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 171 )
public class Carpet extends Block {

    @Override
    public int getBlockId() {
        return 171;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
