package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 271 )
 public class ItemWoodenAxe extends ItemStack implements io.gomint.inventory.item.ItemWoodenAxe {

    // CHECKSTYLE:OFF
    public ItemWoodenAxe( short data, int amount ) {
        super( 271, data, amount );
    }

    public ItemWoodenAxe( short data, int amount, NBTTagCompound nbt ) {
        super( 271, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
