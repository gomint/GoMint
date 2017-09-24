package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 302 )
 public class ItemChainHelmet extends ItemStack implements io.gomint.inventory.item.ItemChainHelmet {

    // CHECKSTYLE:OFF
    public ItemChainHelmet( short data, int amount ) {
        super( 302, data, amount );
    }

    public ItemChainHelmet( short data, int amount, NBTTagCompound nbt ) {
        super( 302, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
