package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 201 )
public class PurpurBlock extends Block {

    @Override
    public int getBlockId() {
        return 201;
    }

    @Override
    public long getBreakTime() {
        return 2250;
    }

}
