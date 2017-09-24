package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 438 )
 public class ItemSplashPotion extends ItemStack implements io.gomint.inventory.item.ItemSplashPotion {

    // CHECKSTYLE:OFF
    public ItemSplashPotion( short data, int amount ) {
        super( 438, data, amount );
    }

    public ItemSplashPotion( short data, int amount, NBTTagCompound nbt ) {
        super( 438, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
