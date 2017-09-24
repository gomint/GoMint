package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 153 )
public class NetherQuartzOre extends Block {

    @Override
    public int getBlockId() {
        return 153;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

}
