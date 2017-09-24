package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 203 )
public class PurpurStairs extends Stairs {

    @Override
    public int getBlockId() {
        return 203;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

}
