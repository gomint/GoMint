package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 423 )
 public class ItemMutton extends ItemStack implements io.gomint.inventory.item.ItemMutton {

    // CHECKSTYLE:OFF
    public ItemMutton( short data, int amount ) {
        super( 423, data, amount );
    }

    public ItemMutton( short data, int amount, NBTTagCompound nbt ) {
        super( 423, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
