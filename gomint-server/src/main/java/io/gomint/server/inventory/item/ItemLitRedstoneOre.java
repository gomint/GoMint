package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 74 )
 public class ItemLitRedstoneOre extends ItemStack implements io.gomint.inventory.item.ItemLitRedstoneOre {

    // CHECKSTYLE:OFF
    public ItemLitRedstoneOre( short data, int amount ) {
        super( 74, data, amount );
    }

    public ItemLitRedstoneOre( short data, int amount, NBTTagCompound nbt ) {
        super( 74, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
