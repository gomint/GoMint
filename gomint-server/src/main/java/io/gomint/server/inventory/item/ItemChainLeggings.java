package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 304 )
 public class ItemChainLeggings extends ItemStack implements io.gomint.inventory.item.ItemChainLeggings {

    // CHECKSTYLE:OFF
    public ItemChainLeggings( short data, int amount ) {
        super( 304, data, amount );
    }

    public ItemChainLeggings( short data, int amount, NBTTagCompound nbt ) {
        super( 304, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
