package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 41 )
 public class ItemGoldBlock extends ItemStack implements io.gomint.inventory.item.ItemGoldBlock {

    // CHECKSTYLE:OFF
    public ItemGoldBlock( short data, int amount ) {
        super( 41, data, amount );
    }

    public ItemGoldBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 41, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
