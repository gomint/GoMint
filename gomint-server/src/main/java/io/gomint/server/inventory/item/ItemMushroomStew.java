package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 282 )
 public class ItemMushroomStew extends ItemStack implements io.gomint.inventory.item.ItemMushroomStew {

    // CHECKSTYLE:OFF
    public ItemMushroomStew( short data, int amount ) {
        super( 282, data, amount );
    }

    public ItemMushroomStew( short data, int amount, NBTTagCompound nbt ) {
        super( 282, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
