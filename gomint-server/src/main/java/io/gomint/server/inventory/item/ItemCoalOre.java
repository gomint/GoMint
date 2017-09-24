package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 16 )
 public class ItemCoalOre extends ItemStack implements io.gomint.inventory.item.ItemCoalOre {

    // CHECKSTYLE:OFF
    public ItemCoalOre( short data, int amount ) {
        super( 16, data, amount );
    }

    public ItemCoalOre( short data, int amount, NBTTagCompound nbt ) {
        super( 16, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
