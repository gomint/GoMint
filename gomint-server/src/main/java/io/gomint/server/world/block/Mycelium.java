package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 110 )
public class Mycelium extends Block {

    @Override
    public int getBlockId() {
        return 110;
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

}
