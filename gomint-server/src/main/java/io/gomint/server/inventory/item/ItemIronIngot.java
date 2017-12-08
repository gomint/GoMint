package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 265 )
 public class ItemIronIngot extends ItemStack implements io.gomint.inventory.item.ItemIronIngot {

    // CHECKSTYLE:OFF
    public ItemIronIngot( short data, int amount ) {
        super( 265, data, amount );
    }

    public ItemIronIngot( short data, int amount, NBTTagCompound nbt ) {
        super( 265, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.IRON_INGOT;
    }

}