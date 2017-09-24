package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 277 )
 public class ItemDiamondShovel extends ItemStack implements io.gomint.inventory.item.ItemDiamondShovel {

    // CHECKSTYLE:OFF
    public ItemDiamondShovel( short data, int amount ) {
        super( 277, data, amount );
    }

    public ItemDiamondShovel( short data, int amount, NBTTagCompound nbt ) {
        super( 277, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
