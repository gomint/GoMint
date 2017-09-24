package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 13 )
public class Gravel extends Block {

    @Override
    public int getBlockId() {
        return 13;
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

}
