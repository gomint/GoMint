package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 91 )
 public class ItemLitPumpkin extends ItemStack implements io.gomint.inventory.item.ItemLitPumpkin {

    // CHECKSTYLE:OFF
    public ItemLitPumpkin( short data, int amount ) {
        super( 91, data, amount );
    }

    public ItemLitPumpkin( short data, int amount, NBTTagCompound nbt ) {
        super( 91, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
