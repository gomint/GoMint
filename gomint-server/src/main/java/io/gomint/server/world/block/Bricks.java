package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 45 )
public class Bricks extends Block {

    @Override
    public int getBlockId() {
        return 45;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

}
