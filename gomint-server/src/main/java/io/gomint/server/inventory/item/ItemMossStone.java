package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 48 )
 public class ItemMossStone extends ItemStack implements io.gomint.inventory.item.ItemMossStone {

    // CHECKSTYLE:OFF
    public ItemMossStone( short data, int amount ) {
        super( 48, data, amount );
    }

    public ItemMossStone( short data, int amount, NBTTagCompound nbt ) {
        super( 48, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
