package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 77 )
public class StoneButton extends Block {

    @Override
    public int getBlockId() {
        return 77;
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

}
