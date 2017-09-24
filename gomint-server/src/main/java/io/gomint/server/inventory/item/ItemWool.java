package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 35 )
 public class ItemWool extends ItemStack implements io.gomint.inventory.item.ItemWool {

    // CHECKSTYLE:OFF
    public ItemWool( short data, int amount ) {
        super( 35, data, amount );
    }

    public ItemWool( short data, int amount, NBTTagCompound nbt ) {
        super( 35, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
