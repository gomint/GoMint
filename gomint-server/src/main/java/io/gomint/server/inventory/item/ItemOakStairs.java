package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 53 )
 public class ItemOakStairs extends ItemStack implements io.gomint.inventory.item.ItemOakStairs {

    // CHECKSTYLE:OFF
    public ItemOakStairs( short data, int amount ) {
        super( 53, data, amount );
    }

    public ItemOakStairs( short data, int amount, NBTTagCompound nbt ) {
        super( 53, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
