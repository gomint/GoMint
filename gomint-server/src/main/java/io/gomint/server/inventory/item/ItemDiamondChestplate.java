package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 311 )
 public class ItemDiamondChestplate extends ItemStack implements io.gomint.inventory.item.ItemDiamondChestplate {

    // CHECKSTYLE:OFF
    public ItemDiamondChestplate( short data, int amount ) {
        super( 311, data, amount );
    }

    public ItemDiamondChestplate( short data, int amount, NBTTagCompound nbt ) {
        super( 311, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
