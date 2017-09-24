package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 329 )
 public class ItemSaddle extends ItemStack implements io.gomint.inventory.item.ItemSaddle {

    // CHECKSTYLE:OFF
    public ItemSaddle( short data, int amount ) {
        super( 329, data, amount );
    }

    public ItemSaddle( short data, int amount, NBTTagCompound nbt ) {
        super( 329, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
