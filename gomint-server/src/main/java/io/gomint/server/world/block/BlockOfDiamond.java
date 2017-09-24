package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 57 )
public class BlockOfDiamond extends Block {

    @Override
    public int getBlockId() {
        return 57;
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

}
