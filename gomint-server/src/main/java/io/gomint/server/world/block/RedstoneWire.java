package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 55 )
public class RedstoneWire extends Block {

    @Override
    public int getBlockId() {
        return 55;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
