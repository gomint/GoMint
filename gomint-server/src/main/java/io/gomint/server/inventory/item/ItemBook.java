package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 340 )
 public class ItemBook extends ItemStack implements io.gomint.inventory.item.ItemBook {

    // CHECKSTYLE:OFF
    public ItemBook( short data, int amount ) {
        super( 340, data, amount );
    }

    public ItemBook( short data, int amount, NBTTagCompound nbt ) {
        super( 340, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
