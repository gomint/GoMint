package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 344 )
 public class ItemEgg extends ItemStack implements io.gomint.inventory.item.ItemEgg {

    // CHECKSTYLE:OFF
    public ItemEgg( short data, int amount ) {
        super( 344, data, amount );
    }

    public ItemEgg( short data, int amount, NBTTagCompound nbt ) {
        super( 344, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
