package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 168 )
 public class ItemPrismarine extends ItemStack implements io.gomint.inventory.item.ItemPrismarine {

    // CHECKSTYLE:OFF
    public ItemPrismarine( short data, int amount ) {
        super( 168, data, amount );
    }

    public ItemPrismarine( short data, int amount, NBTTagCompound nbt ) {
        super( 168, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
