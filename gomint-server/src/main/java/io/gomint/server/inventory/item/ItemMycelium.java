package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 110 )
 public class ItemMycelium extends ItemStack implements io.gomint.inventory.item.ItemMycelium {

    // CHECKSTYLE:OFF
    public ItemMycelium( short data, int amount ) {
        super( 110, data, amount );
    }

    public ItemMycelium( short data, int amount, NBTTagCompound nbt ) {
        super( 110, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
