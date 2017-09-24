package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 12 )
public class Sand extends Block {

    @Override
    public int getBlockId() {
        return 12;
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

}
