package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 124 )
 public class ItemRedstoneLampActive extends ItemStack implements io.gomint.inventory.item.ItemRedstoneLampActive {

    // CHECKSTYLE:OFF
    public ItemRedstoneLampActive( short data, int amount ) {
        super( 124, data, amount );
    }

    public ItemRedstoneLampActive( short data, int amount, NBTTagCompound nbt ) {
        super( 124, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
