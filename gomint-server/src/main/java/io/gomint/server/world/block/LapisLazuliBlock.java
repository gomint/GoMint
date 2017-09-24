package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 22 )
public class LapisLazuliBlock extends Block {

    @Override
    public int getBlockId() {
        return 22;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

}
