package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 56 )
 public class ItemDiamondOre extends ItemStack implements io.gomint.inventory.item.ItemDiamondOre {

    // CHECKSTYLE:OFF
    public ItemDiamondOre( short data, int amount ) {
        super( 56, data, amount );
    }

    public ItemDiamondOre( short data, int amount, NBTTagCompound nbt ) {
        super( 56, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
