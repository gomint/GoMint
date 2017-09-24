package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 22 )
 public class ItemLapisLazuliBlock extends ItemStack implements io.gomint.inventory.item.ItemLapisLazuliBlock {

    // CHECKSTYLE:OFF
    public ItemLapisLazuliBlock( short data, int amount ) {
        super( 22, data, amount );
    }

    public ItemLapisLazuliBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 22, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
