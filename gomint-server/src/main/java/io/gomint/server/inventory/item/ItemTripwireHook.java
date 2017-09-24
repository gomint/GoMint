package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 131 )
 public class ItemTripwireHook extends ItemStack implements io.gomint.inventory.item.ItemTripwireHook {

    // CHECKSTYLE:OFF
    public ItemTripwireHook( short data, int amount ) {
        super( 131, data, amount );
    }

    public ItemTripwireHook( short data, int amount, NBTTagCompound nbt ) {
        super( 131, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
