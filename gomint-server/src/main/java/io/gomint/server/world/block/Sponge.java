package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 19 )
public class Sponge extends Block {

    @Override
    public int getBlockId() {
        return 19;
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

}
