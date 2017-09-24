package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 374 )
 public class ItemGlassBottle extends ItemStack implements io.gomint.inventory.item.ItemGlassBottle {

    // CHECKSTYLE:OFF
    public ItemGlassBottle( short data, int amount ) {
        super( 374, data, amount );
    }

    public ItemGlassBottle( short data, int amount, NBTTagCompound nbt ) {
        super( 374, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
