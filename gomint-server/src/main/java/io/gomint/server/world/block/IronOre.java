package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 15 )
public class IronOre extends Block {

    @Override
    public int getBlockId() {
        return 15;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

}
