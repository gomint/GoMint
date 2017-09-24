package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 87 )
public class Netherrack extends Block {

    @Override
    public int getBlockId() {
        return 87;
    }

    @Override
    public long getBreakTime() {
        return 600;
    }

}
