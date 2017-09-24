package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 47 )
public class Bookshelf extends Block {

    @Override
    public int getBlockId() {
        return 47;
    }

    @Override
    public long getBreakTime() {
        return 2250;
    }

}
