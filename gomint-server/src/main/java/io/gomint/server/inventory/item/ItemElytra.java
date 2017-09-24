package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 444 )
 public class ItemElytra extends ItemStack implements io.gomint.inventory.item.ItemElytra {

    // CHECKSTYLE:OFF
    public ItemElytra( short data, int amount ) {
        super( 444, data, amount );
    }

    public ItemElytra( short data, int amount, NBTTagCompound nbt ) {
        super( 444, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
