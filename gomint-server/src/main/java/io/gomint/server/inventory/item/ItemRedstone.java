package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 331 )
 public class ItemRedstone extends ItemStack implements io.gomint.inventory.item.ItemRedstone {

    // CHECKSTYLE:OFF
    public ItemRedstone( short data, int amount ) {
        super( 331, data, amount );
    }

    public ItemRedstone( short data, int amount, NBTTagCompound nbt ) {
        super( 331, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
