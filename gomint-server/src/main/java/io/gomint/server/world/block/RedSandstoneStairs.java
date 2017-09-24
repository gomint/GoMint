package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 180 )
public class RedSandstoneStairs extends Stairs {

    @Override
    public int getBlockId() {
        return 180;
    }

    @Override
    public long getBreakTime() {
        return 1200;
    }

}
