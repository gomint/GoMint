package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 17 )
 public class ItemWood extends ItemStack implements io.gomint.inventory.item.ItemWood {

    // CHECKSTYLE:OFF
    public ItemWood( short data, int amount ) {
        super( 17, data, amount );
    }

    public ItemWood( short data, int amount, NBTTagCompound nbt ) {
        super( 17, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
