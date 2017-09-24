package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 80 )
 public class ItemSnow extends ItemStack implements io.gomint.inventory.item.ItemSnow {

    // CHECKSTYLE:OFF
    public ItemSnow( short data, int amount ) {
        super( 80, data, amount );
    }

    public ItemSnow( short data, int amount, NBTTagCompound nbt ) {
        super( 80, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
