package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 319 )
 public class ItemRawPorkchop extends ItemStack implements io.gomint.inventory.item.ItemRawPorkchop {

    // CHECKSTYLE:OFF
    public ItemRawPorkchop( short data, int amount ) {
        super( 319, data, amount );
    }

    public ItemRawPorkchop( short data, int amount, NBTTagCompound nbt ) {
        super( 319, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
