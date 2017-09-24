package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 133 )
public class BlockOfEmerald extends Block {

    @Override
    public int getBlockId() {
        return 133;
    }

    @Override
    public long getBreakTime() {
        return 7500;
    }

}
