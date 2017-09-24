package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 1 )
 public class ItemStone extends ItemStack implements io.gomint.inventory.item.ItemStone {

    // CHECKSTYLE:OFF
    public ItemStone( short data, int amount ) {
        super( 1, data, amount );
    }

    public ItemStone( short data, int amount, NBTTagCompound nbt ) {
        super( 1, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
