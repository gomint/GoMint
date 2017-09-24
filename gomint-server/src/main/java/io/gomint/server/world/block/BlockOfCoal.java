package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 173 )
public class BlockOfCoal extends Block {

    @Override
    public int getBlockId() {
        return 173;
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

}
