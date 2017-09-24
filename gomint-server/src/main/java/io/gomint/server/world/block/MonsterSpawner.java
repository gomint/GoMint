package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 52 )
public class MonsterSpawner extends Block {

    @Override
    public int getBlockId() {
        return 52;
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
