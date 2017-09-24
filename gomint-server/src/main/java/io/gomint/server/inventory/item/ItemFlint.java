package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 318 )
 public class ItemFlint extends ItemStack implements io.gomint.inventory.item.ItemFlint {

    // CHECKSTYLE:OFF
    public ItemFlint( short data, int amount ) {
        super( 318, data, amount );
    }

    public ItemFlint( short data, int amount, NBTTagCompound nbt ) {
        super( 318, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
