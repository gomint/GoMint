package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 122 )
 public class ItemDragonEgg extends ItemStack implements io.gomint.inventory.item.ItemDragonEgg {

    // CHECKSTYLE:OFF
    public ItemDragonEgg( short data, int amount ) {
        super( 122, data, amount );
    }

    public ItemDragonEgg( short data, int amount, NBTTagCompound nbt ) {
        super( 122, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
