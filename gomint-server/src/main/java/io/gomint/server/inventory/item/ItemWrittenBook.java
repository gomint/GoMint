package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 387 )
public class ItemWrittenBook extends ItemStack implements io.gomint.inventory.item.ItemWrittenBook {

    // CHECKSTYLE:OFF
    public ItemWrittenBook( short data, int amount ) {
        super( 387, data, amount );
    }

    public ItemWrittenBook( short data, int amount, NBTTagCompound nbt ) {
        super( 387, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.WRITTEN_BOOK;
    }

}
