package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 104 )
 public class ItemPumpkinStem extends ItemStack implements io.gomint.inventory.item.ItemPumpkinStem {

    // CHECKSTYLE:OFF
    public ItemPumpkinStem( short data, int amount ) {
        super( 104, data, amount );
    }

    public ItemPumpkinStem( short data, int amount, NBTTagCompound nbt ) {
        super( 104, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
