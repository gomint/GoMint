package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 30 )
 public class ItemWeb extends ItemStack implements io.gomint.inventory.item.ItemWeb {

    // CHECKSTYLE:OFF
    public ItemWeb( short data, int amount ) {
        super( 30, data, amount );
    }

    public ItemWeb( short data, int amount, NBTTagCompound nbt ) {
        super( 30, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
