package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 100 )
 public class ItemRedMushroomBlock extends ItemStack implements io.gomint.inventory.item.ItemRedMushroomBlock {

    // CHECKSTYLE:OFF
    public ItemRedMushroomBlock( short data, int amount ) {
        super( 100, data, amount );
    }

    public ItemRedMushroomBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 100, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
