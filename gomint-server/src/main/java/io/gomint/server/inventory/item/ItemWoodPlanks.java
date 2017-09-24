package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 5 )
 public class ItemWoodPlanks extends ItemStack implements io.gomint.inventory.item.ItemWoodPlanks {

    // CHECKSTYLE:OFF
    public ItemWoodPlanks( short data, int amount ) {
        super( 5, data, amount );
    }

    public ItemWoodPlanks( short data, int amount, NBTTagCompound nbt ) {
        super( 5, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
