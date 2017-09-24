package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 112 )
public class NetherBrick extends Block {

    @Override
    public int getBlockId() {
        return 112;
    }

    @Override
    public long getBreakTime() {
        return 3000;
    }

}
