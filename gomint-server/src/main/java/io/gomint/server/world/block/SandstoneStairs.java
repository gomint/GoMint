package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 128 )
public class SandstoneStairs extends Stairs {

    @Override
    public int getBlockId() {
        return 128;
    }

    @Override
    public long getBreakTime() {
        return 1200;
    }

}
