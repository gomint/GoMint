package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 142 )
public class Potato extends Block {

    @Override
    public int getBlockId() {
        return 142;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
