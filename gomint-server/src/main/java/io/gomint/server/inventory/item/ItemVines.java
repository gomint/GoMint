package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 106 )
 public class ItemVines extends ItemStack implements io.gomint.inventory.item.ItemVines {

    // CHECKSTYLE:OFF
    public ItemVines( short data, int amount ) {
        super( 106, data, amount );
    }

    public ItemVines( short data, int amount, NBTTagCompound nbt ) {
        super( 106, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
