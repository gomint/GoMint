package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 307 )
 public class ItemIronChestplate extends ItemStack implements io.gomint.inventory.item.ItemIronChestplate {

    // CHECKSTYLE:OFF
    public ItemIronChestplate( short data, int amount ) {
        super( 307, data, amount );
    }

    public ItemIronChestplate( short data, int amount, NBTTagCompound nbt ) {
        super( 307, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
