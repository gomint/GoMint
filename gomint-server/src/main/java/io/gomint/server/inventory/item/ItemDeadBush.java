package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 32 )
 public class ItemDeadBush extends ItemStack implements io.gomint.inventory.item.ItemDeadBush {

    // CHECKSTYLE:OFF
    public ItemDeadBush( short data, int amount ) {
        super( 32, data, amount );
    }

    public ItemDeadBush( short data, int amount, NBTTagCompound nbt ) {
        super( 32, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
