package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 49 )
public class Obsidian extends Block {

    @Override
    public int getBlockId() {
        return 49;
    }

    @Override
    public long getBreakTime() {
        return 75000;
    }

}
