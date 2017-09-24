package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 67 )
 public class ItemCobblestoneStairs extends ItemStack implements io.gomint.inventory.item.ItemCobblestoneStairs {

    // CHECKSTYLE:OFF
    public ItemCobblestoneStairs( short data, int amount ) {
        super( 67, data, amount );
    }

    public ItemCobblestoneStairs( short data, int amount, NBTTagCompound nbt ) {
        super( 67, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
