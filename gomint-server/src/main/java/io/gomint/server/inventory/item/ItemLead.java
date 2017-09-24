package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 420 )
 public class ItemLead extends ItemStack implements io.gomint.inventory.item.ItemLead {

    // CHECKSTYLE:OFF
    public ItemLead( short data, int amount ) {
        super( 420, data, amount );
    }

    public ItemLead( short data, int amount, NBTTagCompound nbt ) {
        super( 420, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
