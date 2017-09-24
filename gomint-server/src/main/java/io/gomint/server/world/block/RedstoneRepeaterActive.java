package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 94 )
public class RedstoneRepeaterActive extends Block {

    @Override
    public int getBlockId() {
        return 94;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
