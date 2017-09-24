package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 249 )
 public class ItemUpdateGameBlockUpdate2 extends ItemStack implements io.gomint.inventory.item.ItemUpdateGameBlockUpdate2 {

    // CHECKSTYLE:OFF
    public ItemUpdateGameBlockUpdate2( short data, int amount ) {
        super( 249, data, amount );
    }

    public ItemUpdateGameBlockUpdate2( short data, int amount, NBTTagCompound nbt ) {
        super( 249, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
