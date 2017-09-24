package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 18 )
 public class ItemLeaves extends ItemStack implements io.gomint.inventory.item.ItemLeaves {

    // CHECKSTYLE:OFF
    public ItemLeaves( short data, int amount ) {
        super( 18, data, amount );
    }

    public ItemLeaves( short data, int amount, NBTTagCompound nbt ) {
        super( 18, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
