package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 80 )
public class Snow extends Block {

    @Override
    public int getBlockId() {
        return 80;
    }

    @Override
    public long getBreakTime() {
        return 300;
    }

}
