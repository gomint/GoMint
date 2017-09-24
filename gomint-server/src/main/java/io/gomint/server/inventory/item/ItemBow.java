package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 261 )
 public class ItemBow extends ItemStack implements io.gomint.inventory.item.ItemBow {

    // CHECKSTYLE:OFF
    public ItemBow( short data, int amount ) {
        super( 261, data, amount );
    }

    public ItemBow( short data, int amount, NBTTagCompound nbt ) {
        super( 261, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
