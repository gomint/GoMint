package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 129 )
public class EmeraldOre extends Block {

    @Override
    public int getBlockId() {
        return 129;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

}
