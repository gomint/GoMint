package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 187 )
public class AcaciaFenceGate extends Block {

    @Override
    public int getBlockId() {
        return 187;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
