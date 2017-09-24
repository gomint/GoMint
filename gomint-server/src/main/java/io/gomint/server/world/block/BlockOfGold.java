package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 41 )
public class BlockOfGold extends Block {

    @Override
    public int getBlockId() {
        return 41;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

}
