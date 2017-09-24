package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 201 )
 public class ItemPurpurBlock extends ItemStack implements io.gomint.inventory.item.ItemPurpurBlock {

    // CHECKSTYLE:OFF
    public ItemPurpurBlock( short data, int amount ) {
        super( 201, data, amount );
    }

    public ItemPurpurBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 201, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
