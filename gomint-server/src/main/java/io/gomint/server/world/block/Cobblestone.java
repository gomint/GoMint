package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 4 )
public class Cobblestone extends Block {

    @Override
    public int getBlockId() {
        return 4;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

}
