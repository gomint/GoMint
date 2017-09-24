package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 305 )
 public class ItemChainBoots extends ItemStack implements io.gomint.inventory.item.ItemChainBoots {

    // CHECKSTYLE:OFF
    public ItemChainBoots( short data, int amount ) {
        super( 305, data, amount );
    }

    public ItemChainBoots( short data, int amount, NBTTagCompound nbt ) {
        super( 305, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
