package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 93 )
 public class ItemRedstoneRepeaterInactive extends ItemStack implements io.gomint.inventory.item.ItemRedstoneRepeaterInactive {

    // CHECKSTYLE:OFF
    public ItemRedstoneRepeaterInactive( short data, int amount ) {
        super( 93, data, amount );
    }

    public ItemRedstoneRepeaterInactive( short data, int amount, NBTTagCompound nbt ) {
        super( 93, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
