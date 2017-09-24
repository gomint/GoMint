package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 14 )
public class GoldOre extends Block {

    @Override
    public int getBlockId() {
        return 14;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

}
