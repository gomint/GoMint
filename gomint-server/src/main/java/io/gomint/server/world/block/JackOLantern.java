package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 91 )
public class JackOLantern extends Block {

    @Override
    public int getBlockId() {
        return 91;
    }

    @Override
    public long getBreakTime() {
        return 1500;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

}
