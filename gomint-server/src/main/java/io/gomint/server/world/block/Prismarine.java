package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 168 )
public class Prismarine extends Block {

    @Override
    public int getBlockId() {
        return 168;
    }

    @Override
    public long getBreakTime() {
        return 2250;
    }

}
