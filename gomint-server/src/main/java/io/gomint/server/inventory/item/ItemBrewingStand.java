package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 379 )
 public class ItemBrewingStand extends ItemStack implements io.gomint.inventory.item.ItemBrewingStand {

    // CHECKSTYLE:OFF
    public ItemBrewingStand( short data, int amount ) {
        super( 379, data, amount );
    }

    public ItemBrewingStand( short data, int amount, NBTTagCompound nbt ) {
        super( 379, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public int getBlockId() {
        return 117;
    }

    @Override
    public ItemType getType() {
        return ItemType.BREWING_STAND;
    }

}