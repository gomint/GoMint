package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 9 )
 public class ItemStationaryWater extends ItemStack implements io.gomint.inventory.item.ItemStationaryWater {

    // CHECKSTYLE:OFF
    public ItemStationaryWater( short data, int amount ) {
        super( 9, data, amount );
    }

    public ItemStationaryWater( short data, int amount, NBTTagCompound nbt ) {
        super( 9, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.STATIONARY_WATER;
    }

}