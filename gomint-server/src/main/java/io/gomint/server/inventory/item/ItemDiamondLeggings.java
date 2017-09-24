package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 312 )
 public class ItemDiamondLeggings extends ItemStack implements io.gomint.inventory.item.ItemDiamondLeggings {

    // CHECKSTYLE:OFF
    public ItemDiamondLeggings( short data, int amount ) {
        super( 312, data, amount );
    }

    public ItemDiamondLeggings( short data, int amount, NBTTagCompound nbt ) {
        super( 312, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
