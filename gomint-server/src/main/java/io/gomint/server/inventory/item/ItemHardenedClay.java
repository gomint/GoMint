package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 172 )
 public class ItemHardenedClay extends ItemStack implements io.gomint.inventory.item.ItemHardenedClay {

    // CHECKSTYLE:OFF
    public ItemHardenedClay( short data, int amount ) {
        super( 172, data, amount );
    }

    public ItemHardenedClay( short data, int amount, NBTTagCompound nbt ) {
        super( 172, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
