package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 241 )
 public class ItemStainedGlass extends ItemStack implements io.gomint.inventory.item.ItemStainedGlass {

    // CHECKSTYLE:OFF
    public ItemStainedGlass( short data, int amount ) {
        super( 241, data, amount );
    }

    public ItemStainedGlass( short data, int amount, NBTTagCompound nbt ) {
        super( 241, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
