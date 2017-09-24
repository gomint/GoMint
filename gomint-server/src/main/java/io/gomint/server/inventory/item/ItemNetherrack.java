package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 87 )
 public class ItemNetherrack extends ItemStack implements io.gomint.inventory.item.ItemNetherrack {

    // CHECKSTYLE:OFF
    public ItemNetherrack( short data, int amount ) {
        super( 87, data, amount );
    }

    public ItemNetherrack( short data, int amount, NBTTagCompound nbt ) {
        super( 87, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
