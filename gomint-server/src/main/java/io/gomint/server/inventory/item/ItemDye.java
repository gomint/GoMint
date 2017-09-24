package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 351 )
 public class ItemDye extends ItemStack implements io.gomint.inventory.item.ItemDye {

    // CHECKSTYLE:OFF
    public ItemDye( short data, int amount ) {
        super( 351, data, amount );
    }

    public ItemDye( short data, int amount, NBTTagCompound nbt ) {
        super( 351, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
