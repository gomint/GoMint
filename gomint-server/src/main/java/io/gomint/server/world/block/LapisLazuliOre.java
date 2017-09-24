package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 21 )
public class LapisLazuliOre extends Block {

    @Override
    public int getBlockId() {
        return 21;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

}
