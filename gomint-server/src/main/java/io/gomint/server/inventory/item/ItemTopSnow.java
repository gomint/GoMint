package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 78 )
 public class ItemTopSnow extends ItemStack implements io.gomint.inventory.item.ItemTopSnow {

    // CHECKSTYLE:OFF
    public ItemTopSnow( short data, int amount ) {
        super( 78, data, amount );
    }

    public ItemTopSnow( short data, int amount, NBTTagCompound nbt ) {
        super( 78, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
