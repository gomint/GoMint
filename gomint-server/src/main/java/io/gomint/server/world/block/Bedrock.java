package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 7 )
public class Bedrock extends Block {

    @Override
    public int getBlockId() {
        return 7;
    }

    @Override
    public long getBreakTime() {
        return -1;
    }

}
