package io.gomint.server.world.block;

import io.gomint.inventory.item.ItemStack;
import io.gomint.math.Vector;
import io.gomint.server.entity.Entity;
import io.gomint.server.entity.tileentity.ChestTileEntity;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 54 )
public class Chest extends Block {

    @Override
    public int getBlockId() {
        return 54;
    }

    @Override
    public long getBreakTime() {
        return 3750;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean interact( Entity entity, int face, Vector facePos, ItemStack item ) {
        ChestTileEntity tileEntity = this.getTileEntity();
        if ( tileEntity != null ) {
            tileEntity.interact( entity, face, facePos, item );
            return true;
        }

        return false;
    }

}
