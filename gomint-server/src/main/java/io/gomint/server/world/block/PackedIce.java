package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 174 )
public class PackedIce extends Block {

    @Override
    public int getBlockId() {
        return 174;
    }

    @Override
    public long getBreakTime() {
        return 750;
    }

}
