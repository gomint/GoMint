package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 3 )
 public class ItemDirt extends ItemStack implements io.gomint.inventory.item.ItemDirt {

    // CHECKSTYLE:OFF
    public ItemDirt( short data, int amount ) {
        super( 3, data, amount );
    }

    public ItemDirt( short data, int amount, NBTTagCompound nbt ) {
        super( 3, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
