package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 141 )
public class Carrots extends Block {

    @Override
    public int getBlockId() {
        return 141;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
