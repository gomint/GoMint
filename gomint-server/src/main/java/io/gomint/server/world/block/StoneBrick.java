package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 98 )
public class StoneBrick extends Block {

    @Override
    public int getBlockId() {
        return 98;
    }

    @Override
    public long getBreakTime() {
        return 2250;
    }

}
