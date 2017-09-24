package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 144 )
 public class ItemMobHead extends ItemStack implements io.gomint.inventory.item.ItemMobHead {

    // CHECKSTYLE:OFF
    public ItemMobHead( short data, int amount ) {
        super( 144, data, amount );
    }

    public ItemMobHead( short data, int amount, NBTTagCompound nbt ) {
        super( 144, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
