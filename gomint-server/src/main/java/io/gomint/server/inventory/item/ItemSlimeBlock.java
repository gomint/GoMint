package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 165 )
 public class ItemSlimeBlock extends ItemStack implements io.gomint.inventory.item.ItemSlimeBlock {

    // CHECKSTYLE:OFF
    public ItemSlimeBlock( short data, int amount ) {
        super( 165, data, amount );
    }

    public ItemSlimeBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 165, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
