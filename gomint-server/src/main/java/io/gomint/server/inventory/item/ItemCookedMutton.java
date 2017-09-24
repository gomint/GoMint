package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 424 )
 public class ItemCookedMutton extends ItemStack implements io.gomint.inventory.item.ItemCookedMutton {

    // CHECKSTYLE:OFF
    public ItemCookedMutton( short data, int amount ) {
        super( 424, data, amount );
    }

    public ItemCookedMutton( short data, int amount, NBTTagCompound nbt ) {
        super( 424, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
