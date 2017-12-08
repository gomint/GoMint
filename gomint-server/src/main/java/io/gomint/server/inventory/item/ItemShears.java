package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 359 )
 public class ItemShears extends ItemStack implements io.gomint.inventory.item.ItemShears {

    // CHECKSTYLE:OFF
    public ItemShears( short data, int amount ) {
        super( 359, data, amount );
    }

    public ItemShears( short data, int amount, NBTTagCompound nbt ) {
        super( 359, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.SHEARS;
    }

}