package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 179 )
public class RedSandstone extends Block {

    @Override
    public int getBlockId() {
        return 179;
    }

    @Override
    public long getBreakTime() {
        return 1200;
    }

}
