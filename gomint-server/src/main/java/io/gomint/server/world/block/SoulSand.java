package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 88 )
public class SoulSand extends Block {

    @Override
    public int getBlockId() {
        return 88;
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

}
