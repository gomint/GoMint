package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 159 )
public class StainedClay extends Block {

    @Override
    public int getBlockId() {
        return 159;
    }

    @Override
    public long getBreakTime() {
        return 1875;
    }

}
