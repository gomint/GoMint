package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 40 )
 public class ItemRedMushroom extends ItemStack implements io.gomint.inventory.item.ItemRedMushroom {

    // CHECKSTYLE:OFF
    public ItemRedMushroom( short data, int amount ) {
        super( 40, data, amount );
    }

    public ItemRedMushroom( short data, int amount, NBTTagCompound nbt ) {
        super( 40, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
