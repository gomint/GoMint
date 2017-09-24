package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 112 )
 public class ItemNetherBrickBlock extends ItemStack implements io.gomint.inventory.item.ItemNetherBrickBlock {

    // CHECKSTYLE:OFF
    public ItemNetherBrickBlock( short data, int amount ) {
        super( 112, data, amount );
    }

    public ItemNetherBrickBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 112, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
