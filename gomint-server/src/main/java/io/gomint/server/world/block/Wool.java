package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 35 )
public class Wool extends Block {

    @Override
    public int getBlockId() {
        return 35;
    }

    @Override
    public long getBreakTime() {
        return 1200;
    }

}
