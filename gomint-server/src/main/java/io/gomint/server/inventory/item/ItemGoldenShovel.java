package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 284 )
 public class ItemGoldenShovel extends ItemStack implements io.gomint.inventory.item.ItemGoldenShovel {

    // CHECKSTYLE:OFF
    public ItemGoldenShovel( short data, int amount ) {
        super( 284, data, amount );
    }

    public ItemGoldenShovel( short data, int amount, NBTTagCompound nbt ) {
        super( 284, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
