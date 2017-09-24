package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 163 )
public class AcaciaWoodStairs extends Stairs {

    @Override
    public int getBlockId() {
        return 163;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

}
