package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 280 )
public class ItemStick extends ItemStack implements io.gomint.inventory.item.ItemStick {

    // CHECKSTYLE:OFF
    public ItemStick( short data, int amount ) {
        super( 280, data, amount );
    }

    public ItemStick( short data, int amount, NBTTagCompound nbt ) {
        super( 280, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.STICK;
    }

}