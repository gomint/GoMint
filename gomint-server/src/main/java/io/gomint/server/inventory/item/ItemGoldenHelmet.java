package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 314 )
 public class ItemGoldenHelmet extends ItemStack implements io.gomint.inventory.item.ItemGoldenHelmet {

    // CHECKSTYLE:OFF
    public ItemGoldenHelmet( short data, int amount ) {
        super( 314, data, amount );
    }

    public ItemGoldenHelmet( short data, int amount, NBTTagCompound nbt ) {
        super( 314, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
