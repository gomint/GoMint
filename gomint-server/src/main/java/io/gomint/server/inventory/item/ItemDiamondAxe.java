package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 279 )
 public class ItemDiamondAxe extends ItemStack implements io.gomint.inventory.item.ItemDiamondAxe {

    // CHECKSTYLE:OFF
    public ItemDiamondAxe( short data, int amount ) {
        super( 279, data, amount );
    }

    public ItemDiamondAxe( short data, int amount, NBTTagCompound nbt ) {
        super( 279, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
