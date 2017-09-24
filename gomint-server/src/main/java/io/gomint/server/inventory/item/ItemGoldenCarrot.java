package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 396 )
 public class ItemGoldenCarrot extends ItemStack implements io.gomint.inventory.item.ItemGoldenCarrot {

    // CHECKSTYLE:OFF
    public ItemGoldenCarrot( short data, int amount ) {
        super( 396, data, amount );
    }

    public ItemGoldenCarrot( short data, int amount, NBTTagCompound nbt ) {
        super( 396, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
