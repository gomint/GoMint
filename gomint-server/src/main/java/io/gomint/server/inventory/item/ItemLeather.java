package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 334 )
 public class ItemLeather extends ItemStack implements io.gomint.inventory.item.ItemLeather {

    // CHECKSTYLE:OFF
    public ItemLeather( short data, int amount ) {
        super( 334, data, amount );
    }

    public ItemLeather( short data, int amount, NBTTagCompound nbt ) {
        super( 334, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.LEATHER;
    }

}