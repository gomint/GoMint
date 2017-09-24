package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 127 )
 public class ItemCocoa extends ItemStack implements io.gomint.inventory.item.ItemCocoa {

    // CHECKSTYLE:OFF
    public ItemCocoa( short data, int amount ) {
        super( 127, data, amount );
    }

    public ItemCocoa( short data, int amount, NBTTagCompound nbt ) {
        super( 127, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
