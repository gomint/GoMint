package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 321 )
 public class ItemPainting extends ItemStack implements io.gomint.inventory.item.ItemPainting {

    // CHECKSTYLE:OFF
    public ItemPainting( short data, int amount ) {
        super( 321, data, amount );
    }

    public ItemPainting( short data, int amount, NBTTagCompound nbt ) {
        super( 321, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
