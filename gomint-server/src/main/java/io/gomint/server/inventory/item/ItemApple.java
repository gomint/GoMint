package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 260 )
 public class ItemApple extends ItemStack implements io.gomint.inventory.item.ItemApple {

    // CHECKSTYLE:OFF
    public ItemApple( short data, int amount ) {
        super( 260, data, amount );
    }

    public ItemApple( short data, int amount, NBTTagCompound nbt ) {
        super( 260, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
