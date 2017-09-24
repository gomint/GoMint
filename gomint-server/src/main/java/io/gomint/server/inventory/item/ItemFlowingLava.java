package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 11 )
 public class ItemFlowingLava extends ItemStack implements io.gomint.inventory.item.ItemFlowingLava {

    // CHECKSTYLE:OFF
    public ItemFlowingLava( short data, int amount ) {
        super( 11, data, amount );
    }

    public ItemFlowingLava( short data, int amount, NBTTagCompound nbt ) {
        super( 11, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
