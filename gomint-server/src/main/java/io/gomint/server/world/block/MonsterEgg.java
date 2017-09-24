package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 97 )
public class MonsterEgg extends Block {

    @Override
    public int getBlockId() {
        return 97;
    }

    @Override
    public long getBreakTime() {
        return 1125;
    }

}
