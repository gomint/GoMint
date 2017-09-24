package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 86 )
 public class ItemPumpkin extends ItemStack implements io.gomint.inventory.item.ItemPumpkin {

    // CHECKSTYLE:OFF
    public ItemPumpkin( short data, int amount ) {
        super( 86, data, amount );
    }

    public ItemPumpkin( short data, int amount, NBTTagCompound nbt ) {
        super( 86, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
