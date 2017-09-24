package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 135 )
 public class ItemBirchWoodStairs extends ItemStack implements io.gomint.inventory.item.ItemBirchWoodStairs {

    // CHECKSTYLE:OFF
    public ItemBirchWoodStairs( short data, int amount ) {
        super( 135, data, amount );
    }

    public ItemBirchWoodStairs( short data, int amount, NBTTagCompound nbt ) {
        super( 135, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
