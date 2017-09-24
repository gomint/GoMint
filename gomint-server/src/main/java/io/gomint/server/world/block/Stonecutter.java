package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 245 )
public class Stonecutter extends Block {

    @Override
    public int getBlockId() {
        return 245;
    }

    @Override
    public long getBreakTime() {
        return 5250;
    }

}
