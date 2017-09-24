package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 209 )
public class EndGateway extends Block {

    @Override
    public int getBlockId() {
        return 209;
    }

    @Override
    public long getBreakTime() {
        return -1;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
