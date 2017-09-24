package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 412 )
 public class ItemCookedRabbit extends ItemStack implements io.gomint.inventory.item.ItemCookedRabbit {

    // CHECKSTYLE:OFF
    public ItemCookedRabbit( short data, int amount ) {
        super( 412, data, amount );
    }

    public ItemCookedRabbit( short data, int amount, NBTTagCompound nbt ) {
        super( 412, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
