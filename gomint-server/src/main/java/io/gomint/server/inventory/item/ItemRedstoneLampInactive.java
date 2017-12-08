package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 123 )
 public class ItemRedstoneLampInactive extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemRedstoneLampInactive( short data, int amount ) {
        super( 123, data, amount );
    }

    public ItemRedstoneLampInactive( short data, int amount, NBTTagCompound nbt ) {
        super( 123, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.REDSTONE_LAMP_INACTIVE;
    }

}
