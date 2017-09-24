package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 320 )
 public class ItemCookedPorkchop extends ItemStack implements io.gomint.inventory.item.ItemCookedPorkchop {

    // CHECKSTYLE:OFF
    public ItemCookedPorkchop( short data, int amount ) {
        super( 320, data, amount );
    }

    public ItemCookedPorkchop( short data, int amount, NBTTagCompound nbt ) {
        super( 320, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
