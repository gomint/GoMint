package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 360 )
 public class ItemMelon extends ItemStack implements io.gomint.inventory.item.ItemMelon {

    // CHECKSTYLE:OFF
    public ItemMelon( short data, int amount ) {
        super( 360, data, amount );
    }

    public ItemMelon( short data, int amount, NBTTagCompound nbt ) {
        super( 360, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
