package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 149 )
public class RedstoneComparatorUnpowered extends Block {

    @Override
    public int getBlockId() {
        return 149;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
