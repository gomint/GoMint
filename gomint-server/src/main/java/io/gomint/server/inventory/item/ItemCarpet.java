package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 171 )
 public class ItemCarpet extends ItemStack implements io.gomint.inventory.item.ItemCarpet {

    // CHECKSTYLE:OFF
    public ItemCarpet( short data, int amount ) {
        super( 171, data, amount );
    }

    public ItemCarpet( short data, int amount, NBTTagCompound nbt ) {
        super( 171, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.CARPET;
    }

}