package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 61 )
public class Furnace extends Block {

    @Override
    public int getBlockId() {
        return 61;
    }

    @Override
    public long getBreakTime() {
        return 5250;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
