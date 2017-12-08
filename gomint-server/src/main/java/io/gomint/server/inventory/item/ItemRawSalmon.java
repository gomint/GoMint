package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 460 )
 public class ItemRawSalmon extends ItemStack implements io.gomint.inventory.item.ItemRawSalmon {

    // CHECKSTYLE:OFF
    public ItemRawSalmon( short data, int amount ) {
        super( 460, data, amount );
    }

    public ItemRawSalmon( short data, int amount, NBTTagCompound nbt ) {
        super( 460, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.RAW_SALMON;
    }

}