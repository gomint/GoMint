package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 60 )
 public class ItemFarmland extends ItemStack implements io.gomint.inventory.item.ItemFarmland {

    // CHECKSTYLE:OFF
    public ItemFarmland( short data, int amount ) {
        super( 60, data, amount );
    }

    public ItemFarmland( short data, int amount, NBTTagCompound nbt ) {
        super( 60, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
