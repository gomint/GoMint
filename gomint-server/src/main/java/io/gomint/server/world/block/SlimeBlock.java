package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 165 )
public class SlimeBlock extends Block {

    @Override
    public int getBlockId() {
        return 165;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
