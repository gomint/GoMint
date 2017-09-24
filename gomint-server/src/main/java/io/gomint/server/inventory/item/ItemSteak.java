package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 364 )
 public class ItemSteak extends ItemStack implements io.gomint.inventory.item.ItemSteak {

    // CHECKSTYLE:OFF
    public ItemSteak( short data, int amount ) {
        super( 364, data, amount );
    }

    public ItemSteak( short data, int amount, NBTTagCompound nbt ) {
        super( 364, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
