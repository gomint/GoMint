package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 30 )
 public class ItemCobweb extends ItemStack implements io.gomint.inventory.item.ItemCobweb {

    // CHECKSTYLE:OFF
    public ItemCobweb( short data, int amount ) {
        super( 30, data, amount );
    }

    public ItemCobweb( short data, int amount, NBTTagCompound nbt ) {
        super( 30, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
