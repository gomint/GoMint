package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 79 )
 public class ItemIce extends ItemStack implements io.gomint.inventory.item.ItemIce {

    // CHECKSTYLE:OFF
    public ItemIce( short data, int amount ) {
        super( 79, data, amount );
    }

    public ItemIce( short data, int amount, NBTTagCompound nbt ) {
        super( 79, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
