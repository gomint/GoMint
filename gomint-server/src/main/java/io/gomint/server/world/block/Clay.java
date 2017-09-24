package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 82 )
public class Clay extends Block {

    @Override
    public int getBlockId() {
        return 82;
    }

    @Override
    public long getBreakTime() {
        return 900;
    }

}
