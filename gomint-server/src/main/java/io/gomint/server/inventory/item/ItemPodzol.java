package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 243 )
 public class ItemPodzol extends ItemStack implements io.gomint.inventory.item.ItemPodzol {

    // CHECKSTYLE:OFF
    public ItemPodzol( short data, int amount ) {
        super( 243, data, amount );
    }

    public ItemPodzol( short data, int amount, NBTTagCompound nbt ) {
        super( 243, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
