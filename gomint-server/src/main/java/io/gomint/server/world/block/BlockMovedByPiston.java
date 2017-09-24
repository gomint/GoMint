package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 250 )
public class BlockMovedByPiston extends Block {

    @Override
    public int getBlockId() {
        return 250;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
