package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 183 )
public class SpruceFenceGate extends Block {

    @Override
    public int getBlockId() {
        return 183;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
