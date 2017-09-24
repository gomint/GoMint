package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 303 )
 public class ItemChainChestplate extends ItemStack implements io.gomint.inventory.item.ItemChainChestplate {

    // CHECKSTYLE:OFF
    public ItemChainChestplate( short data, int amount ) {
        super( 303, data, amount );
    }

    public ItemChainChestplate( short data, int amount, NBTTagCompound nbt ) {
        super( 303, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
