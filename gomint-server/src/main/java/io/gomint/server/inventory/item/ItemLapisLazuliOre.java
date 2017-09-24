package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 21 )
 public class ItemLapisLazuliOre extends ItemStack implements io.gomint.inventory.item.ItemLapisLazuliOre {

    // CHECKSTYLE:OFF
    public ItemLapisLazuliOre( short data, int amount ) {
        super( 21, data, amount );
    }

    public ItemLapisLazuliOre( short data, int amount, NBTTagCompound nbt ) {
        super( 21, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
