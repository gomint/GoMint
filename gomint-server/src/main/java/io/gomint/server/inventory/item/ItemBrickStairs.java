package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 108 )
 public class ItemBrickStairs extends ItemStack implements io.gomint.inventory.item.ItemBrickStairs {

    // CHECKSTYLE:OFF
    public ItemBrickStairs( short data, int amount ) {
        super( 108, data, amount );
    }

    public ItemBrickStairs( short data, int amount, NBTTagCompound nbt ) {
        super( 108, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
