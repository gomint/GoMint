package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 275 )
 public class ItemStoneAxe extends ItemStack implements io.gomint.inventory.item.ItemStoneAxe {

    // CHECKSTYLE:OFF
    public ItemStoneAxe( short data, int amount ) {
        super( 275, data, amount );
    }

    public ItemStoneAxe( short data, int amount, NBTTagCompound nbt ) {
        super( 275, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
