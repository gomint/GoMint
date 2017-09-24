package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 333 )
 public class ItemBoat extends ItemStack implements io.gomint.inventory.item.ItemBoat {

    // CHECKSTYLE:OFF
    public ItemBoat( short data, int amount ) {
        super( 333, data, amount );
    }

    public ItemBoat( short data, int amount, NBTTagCompound nbt ) {
        super( 333, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
