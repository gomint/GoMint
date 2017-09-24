package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 125 )
public class Dropper extends Block {

    @Override
    public int getBlockId() {
        return 125;
    }

    @Override
    public long getBreakTime() {
        return 5250;
    }

}
