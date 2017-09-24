package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 126 )
 public class ItemActivatorRail extends ItemStack implements io.gomint.inventory.item.ItemActivatorRail {

    // CHECKSTYLE:OFF
    public ItemActivatorRail( short data, int amount ) {
        super( 126, data, amount );
    }

    public ItemActivatorRail( short data, int amount, NBTTagCompound nbt ) {
        super( 126, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
