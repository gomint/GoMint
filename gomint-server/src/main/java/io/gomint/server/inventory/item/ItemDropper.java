package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 125 )
 public class ItemDropper extends ItemStack implements io.gomint.inventory.item.ItemDropper {

    // CHECKSTYLE:OFF
    public ItemDropper( short data, int amount ) {
        super( 125, data, amount );
    }

    public ItemDropper( short data, int amount, NBTTagCompound nbt ) {
        super( 125, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
