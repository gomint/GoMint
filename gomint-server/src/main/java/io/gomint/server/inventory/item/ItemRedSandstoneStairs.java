package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 180 )
 public class ItemRedSandstoneStairs extends ItemStack implements io.gomint.inventory.item.ItemRedSandstoneStairs {

    // CHECKSTYLE:OFF
    public ItemRedSandstoneStairs( short data, int amount ) {
        super( 180, data, amount );
    }

    public ItemRedSandstoneStairs( short data, int amount, NBTTagCompound nbt ) {
        super( 180, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
