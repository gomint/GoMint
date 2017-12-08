package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 408 )
 public class ItemMinecartWithHopper extends ItemStack implements io.gomint.inventory.item.ItemMinecartWithHopper {

    // CHECKSTYLE:OFF
    public ItemMinecartWithHopper( short data, int amount ) {
        super( 408, data, amount );
    }

    public ItemMinecartWithHopper( short data, int amount, NBTTagCompound nbt ) {
        super( 408, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.MINECART_WITH_HOPPER;
    }

}