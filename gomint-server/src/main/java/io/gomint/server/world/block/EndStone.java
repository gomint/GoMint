package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 121 )
public class EndStone extends Block {

    @Override
    public int getBlockId() {
        return 121;
    }

    @Override
    public long getBreakTime() {
        return 4500;
    }

}
