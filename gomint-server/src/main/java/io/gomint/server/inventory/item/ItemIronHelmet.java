package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 306 )
 public class ItemIronHelmet extends ItemStack implements io.gomint.inventory.item.ItemIronHelmet {

    // CHECKSTYLE:OFF
    public ItemIronHelmet( short data, int amount ) {
        super( 306, data, amount );
    }

    public ItemIronHelmet( short data, int amount, NBTTagCompound nbt ) {
        super( 306, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
