package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 143 )
 public class ItemWoodenButton extends ItemStack implements io.gomint.inventory.item.ItemWoodenButton {

    // CHECKSTYLE:OFF
    public ItemWoodenButton( short data, int amount ) {
        super( 143, data, amount );
    }

    public ItemWoodenButton( short data, int amount, NBTTagCompound nbt ) {
        super( 143, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
