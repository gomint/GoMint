package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 167 )
 public class ItemIronTrapdoor extends ItemStack implements io.gomint.inventory.item.ItemIronTrapdoor {

    // CHECKSTYLE:OFF
    public ItemIronTrapdoor( short data, int amount ) {
        super( 167, data, amount );
    }

    public ItemIronTrapdoor( short data, int amount, NBTTagCompound nbt ) {
        super( 167, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
