package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 66 )
 public class ItemRail extends ItemStack implements io.gomint.inventory.item.ItemRail {

    // CHECKSTYLE:OFF
    public ItemRail( short data, int amount ) {
        super( 66, data, amount );
    }

    public ItemRail( short data, int amount, NBTTagCompound nbt ) {
        super( 66, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.RAIL;
    }

}