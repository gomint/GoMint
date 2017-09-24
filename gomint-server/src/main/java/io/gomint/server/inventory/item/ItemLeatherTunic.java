package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 299 )
 public class ItemLeatherTunic extends ItemStack implements io.gomint.inventory.item.ItemLeatherTunic {

    // CHECKSTYLE:OFF
    public ItemLeatherTunic( short data, int amount ) {
        super( 299, data, amount );
    }

    public ItemLeatherTunic( short data, int amount, NBTTagCompound nbt ) {
        super( 299, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
