package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 150 )
public class RedstoneComparatorPowered extends Block {

    @Override
    public int getBlockId() {
        return 150;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
