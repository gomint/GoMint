package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 156 )
public class QuartzStairs extends Stairs {

    @Override
    public int getBlockId() {
        return 156;
    }

    @Override
    public long getBreakTime() {
        return 1200;
    }

}
