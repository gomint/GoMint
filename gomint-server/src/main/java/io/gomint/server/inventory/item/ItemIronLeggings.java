package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 308 )
 public class ItemIronLeggings extends ItemStack implements io.gomint.inventory.item.ItemIronLeggings {

    // CHECKSTYLE:OFF
    public ItemIronLeggings( short data, int amount ) {
        super( 308, data, amount );
    }

    public ItemIronLeggings( short data, int amount, NBTTagCompound nbt ) {
        super( 308, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
