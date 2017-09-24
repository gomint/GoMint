package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 21 )
 public class ItemLapisOre extends ItemStack implements io.gomint.inventory.item.ItemLapisOre {

    // CHECKSTYLE:OFF
    public ItemLapisOre( short data, int amount ) {
        super( 21, data, amount );
    }

    public ItemLapisOre( short data, int amount, NBTTagCompound nbt ) {
        super( 21, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
