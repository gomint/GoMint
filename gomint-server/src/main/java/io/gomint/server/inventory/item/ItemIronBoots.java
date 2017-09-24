package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 309 )
 public class ItemIronBoots extends ItemStack implements io.gomint.inventory.item.ItemIronBoots {

    // CHECKSTYLE:OFF
    public ItemIronBoots( short data, int amount ) {
        super( 309, data, amount );
    }

    public ItemIronBoots( short data, int amount, NBTTagCompound nbt ) {
        super( 309, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
