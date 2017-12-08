package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 27 )
 public class ItemPoweredRail extends ItemStack implements io.gomint.inventory.item.ItemPoweredRail {

    // CHECKSTYLE:OFF
    public ItemPoweredRail( short data, int amount ) {
        super( 27, data, amount );
    }

    public ItemPoweredRail( short data, int amount, NBTTagCompound nbt ) {
        super( 27, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.POWERED_RAIL;
    }

}