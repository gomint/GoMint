package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 164 )
 public class ItemDarkOakWoodStairs extends ItemStack implements io.gomint.inventory.item.ItemDarkOakWoodStairs {

    // CHECKSTYLE:OFF
    public ItemDarkOakWoodStairs( short data, int amount ) {
        super( 164, data, amount );
    }

    public ItemDarkOakWoodStairs( short data, int amount, NBTTagCompound nbt ) {
        super( 164, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
