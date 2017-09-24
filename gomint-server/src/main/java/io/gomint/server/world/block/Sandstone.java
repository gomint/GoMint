package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 24 )
public class Sandstone extends Block {

    @Override
    public int getBlockId() {
        return 24;
    }

    @Override
    public long getBreakTime() {
        return 1200;
    }

}
