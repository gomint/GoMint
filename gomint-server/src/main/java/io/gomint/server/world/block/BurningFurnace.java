package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 62 )
public class BurningFurnace extends Block {

    @Override
    public int getBlockId() {
        return 62;
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
