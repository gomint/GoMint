package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 291 )
 public class ItemStoneHoe extends ItemStack implements io.gomint.inventory.item.ItemStoneHoe {

    // CHECKSTYLE:OFF
    public ItemStoneHoe( short data, int amount ) {
        super( 291, data, amount );
    }

    public ItemStoneHoe( short data, int amount, NBTTagCompound nbt ) {
        super( 291, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
