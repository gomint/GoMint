package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 384 )
 public class ItemExperienceBottle extends ItemStack implements io.gomint.inventory.item.ItemExperienceBottle {

    // CHECKSTYLE:OFF
    public ItemExperienceBottle( short data, int amount ) {
        super( 384, data, amount );
    }

    public ItemExperienceBottle( short data, int amount, NBTTagCompound nbt ) {
        super( 384, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
