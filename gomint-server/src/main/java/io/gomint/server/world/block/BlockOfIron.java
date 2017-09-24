package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 42 )
public class BlockOfIron extends Block {

    @Override
    public int getBlockId() {
        return 42;
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

}
