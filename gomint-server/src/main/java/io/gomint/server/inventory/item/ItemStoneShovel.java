package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 273 )
 public class ItemStoneShovel extends ItemStack implements io.gomint.inventory.item.ItemStoneShovel {

    // CHECKSTYLE:OFF
    public ItemStoneShovel( short data, int amount ) {
        super( 273, data, amount );
    }

    public ItemStoneShovel( short data, int amount, NBTTagCompound nbt ) {
        super( 273, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
