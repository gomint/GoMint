package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 10 )
 public class ItemLava extends ItemStack implements io.gomint.inventory.item.ItemLava {

    // CHECKSTYLE:OFF
    public ItemLava( short data, int amount ) {
        super( 10, data, amount );
    }

    public ItemLava( short data, int amount, NBTTagCompound nbt ) {
        super( 10, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
