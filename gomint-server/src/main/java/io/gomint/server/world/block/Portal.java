package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 90 )
public class Portal extends Block {

    @Override
    public int getBlockId() {
        return 90;
    }

    @Override
    public long getBreakTime() {
        return -1;
    }

}
