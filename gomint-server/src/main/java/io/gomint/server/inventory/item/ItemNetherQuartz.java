package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 406 )
 public class ItemNetherQuartz extends ItemStack implements io.gomint.inventory.item.ItemNetherQuartz {

    // CHECKSTYLE:OFF
    public ItemNetherQuartz( short data, int amount ) {
        super( 406, data, amount );
    }

    public ItemNetherQuartz( short data, int amount, NBTTagCompound nbt ) {
        super( 406, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
