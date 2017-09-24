package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 0 )
public class Air extends Block implements io.gomint.world.block.Air {

    @Override
    public int getBlockId() {
        return 0;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    @Override
    public boolean canBeReplaced( ItemStack item ) {
        return true;
    }

}
