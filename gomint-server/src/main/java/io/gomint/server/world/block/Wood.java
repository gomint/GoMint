package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 17 )
public class Wood extends Block {

    @Override
    public int getBlockId() {
        return 17;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

}
