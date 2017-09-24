package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 136 )
public class JungleWoodStairs extends Stairs {

    @Override
    public int getBlockId() {
        return 136;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

}
