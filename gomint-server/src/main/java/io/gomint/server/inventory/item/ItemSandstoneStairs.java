package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 128 )
 public class ItemSandstoneStairs extends ItemStack implements io.gomint.inventory.item.ItemSandstoneStairs {

    // CHECKSTYLE:OFF
    public ItemSandstoneStairs( short data, int amount ) {
        super( 128, data, amount );
    }

    public ItemSandstoneStairs( short data, int amount, NBTTagCompound nbt ) {
        super( 128, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
