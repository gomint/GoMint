package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 377 )
 public class ItemBlazePowder extends ItemStack implements io.gomint.inventory.item.ItemBlazePowder {

    // CHECKSTYLE:OFF
    public ItemBlazePowder( short data, int amount ) {
        super( 377, data, amount );
    }

    public ItemBlazePowder( short data, int amount, NBTTagCompound nbt ) {
        super( 377, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
