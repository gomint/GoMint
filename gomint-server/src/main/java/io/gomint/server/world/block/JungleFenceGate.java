package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 185 )
public class JungleFenceGate extends Block {

    @Override
    public int getBlockId() {
        return 185;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
