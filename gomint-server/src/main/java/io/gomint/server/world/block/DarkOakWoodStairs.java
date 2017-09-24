package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 164 )
public class DarkOakWoodStairs extends Stairs {

    @Override
    public int getBlockId() {
        return 164;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

}
