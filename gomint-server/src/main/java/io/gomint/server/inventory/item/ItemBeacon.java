package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 138 )
 public class ItemBeacon extends ItemStack implements io.gomint.inventory.item.ItemBeacon {

    // CHECKSTYLE:OFF
    public ItemBeacon( short data, int amount ) {
        super( 138, data, amount );
    }

    public ItemBeacon( short data, int amount, NBTTagCompound nbt ) {
        super( 138, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
