package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 313 )
 public class ItemDiamondBoots extends ItemStack implements io.gomint.inventory.item.ItemDiamondBoots {

    // CHECKSTYLE:OFF
    public ItemDiamondBoots( short data, int amount ) {
        super( 313, data, amount );
    }

    public ItemDiamondBoots( short data, int amount, NBTTagCompound nbt ) {
        super( 313, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
