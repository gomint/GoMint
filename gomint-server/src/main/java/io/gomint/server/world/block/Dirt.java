package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 3 )
public class Dirt extends Block implements io.gomint.world.block.Dirt {

    @Override
    public int getBlockId() {
        return 3;
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

}
