package io.gomint.server.world.block;

import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 100 )
public class RedMushroomBlock extends Block {

    @Override
    public int getBlockId() {
        return 100;
    }

}
