package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 54 )
 public class ItemChest extends ItemStack implements io.gomint.inventory.item.ItemChest {

    // CHECKSTYLE:OFF
    public ItemChest( short data, int amount ) {
        super( 54, data, amount );
    }

    public ItemChest( short data, int amount, NBTTagCompound nbt ) {
        super( 54, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
