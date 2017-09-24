package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 170 )
public class HayBale extends Block {

    @Override
    public int getBlockId() {
        return 170;
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

}
