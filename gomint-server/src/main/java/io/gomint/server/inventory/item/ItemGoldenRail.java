package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 27 )
 public class ItemGoldenRail extends ItemStack implements io.gomint.inventory.item.ItemGoldenRail {

    // CHECKSTYLE:OFF
    public ItemGoldenRail( short data, int amount ) {
        super( 27, data, amount );
    }

    public ItemGoldenRail( short data, int amount, NBTTagCompound nbt ) {
        super( 27, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
