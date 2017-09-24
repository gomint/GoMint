package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 293 )
 public class ItemDiamondHoe extends ItemStack implements io.gomint.inventory.item.ItemDiamondHoe {

    // CHECKSTYLE:OFF
    public ItemDiamondHoe( short data, int amount ) {
        super( 293, data, amount );
    }

    public ItemDiamondHoe( short data, int amount, NBTTagCompound nbt ) {
        super( 293, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
