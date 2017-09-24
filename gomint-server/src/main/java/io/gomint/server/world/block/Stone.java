package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 1 )
public class Stone extends Block {

    @Override
    public int getBlockId() {
        return 1;
    }

    @Override
    public long getBreakTime() {
        return 2250;
    }

}
