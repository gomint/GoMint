package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 346 )
 public class ItemFishingRod extends ItemStack implements io.gomint.inventory.item.ItemFishingRod {

    // CHECKSTYLE:OFF
    public ItemFishingRod( short data, int amount ) {
        super( 346, data, amount );
    }

    public ItemFishingRod( short data, int amount, NBTTagCompound nbt ) {
        super( 346, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
