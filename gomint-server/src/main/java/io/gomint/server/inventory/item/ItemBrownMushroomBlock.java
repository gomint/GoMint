package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 99 )
 public class ItemBrownMushroomBlock extends ItemStack implements io.gomint.inventory.item.ItemBrownMushroomBlock {

    // CHECKSTYLE:OFF
    public ItemBrownMushroomBlock( short data, int amount ) {
        super( 99, data, amount );
    }

    public ItemBrownMushroomBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 99, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
