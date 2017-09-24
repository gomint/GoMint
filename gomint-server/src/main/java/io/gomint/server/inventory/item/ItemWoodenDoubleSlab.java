package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 157 )
 public class ItemWoodenDoubleSlab extends ItemStack implements io.gomint.inventory.item.ItemWoodenDoubleSlab {

    // CHECKSTYLE:OFF
    public ItemWoodenDoubleSlab( short data, int amount ) {
        super( 157, data, amount );
    }

    public ItemWoodenDoubleSlab( short data, int amount, NBTTagCompound nbt ) {
        super( 157, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
