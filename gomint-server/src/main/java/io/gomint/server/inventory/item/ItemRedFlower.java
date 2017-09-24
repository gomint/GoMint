package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 38 )
 public class ItemRedFlower extends ItemStack implements io.gomint.inventory.item.ItemRedFlower {

    // CHECKSTYLE:OFF
    public ItemRedFlower( short data, int amount ) {
        super( 38, data, amount );
    }

    public ItemRedFlower( short data, int amount, NBTTagCompound nbt ) {
        super( 38, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
