package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 20 )
 public class ItemGlass extends ItemStack implements io.gomint.inventory.item.ItemGlass {

    // CHECKSTYLE:OFF
    public ItemGlass( short data, int amount ) {
        super( 20, data, amount );
    }

    public ItemGlass( short data, int amount, NBTTagCompound nbt ) {
        super( 20, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
