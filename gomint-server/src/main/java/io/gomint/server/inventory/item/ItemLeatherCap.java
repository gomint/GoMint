package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 298 )
 public class ItemLeatherCap extends ItemStack implements io.gomint.inventory.item.ItemLeatherCap {

    // CHECKSTYLE:OFF
    public ItemLeatherCap( short data, int amount ) {
        super( 298, data, amount );
    }

    public ItemLeatherCap( short data, int amount, NBTTagCompound nbt ) {
        super( 298, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
