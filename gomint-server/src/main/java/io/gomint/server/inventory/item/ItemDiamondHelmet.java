package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 310 )
 public class ItemDiamondHelmet extends ItemStack implements io.gomint.inventory.item.ItemDiamondHelmet {

    // CHECKSTYLE:OFF
    public ItemDiamondHelmet( short data, int amount ) {
        super( 310, data, amount );
    }

    public ItemDiamondHelmet( short data, int amount, NBTTagCompound nbt ) {
        super( 310, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
