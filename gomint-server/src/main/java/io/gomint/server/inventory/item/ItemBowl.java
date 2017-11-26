package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 281 )
 public class ItemBowl extends ItemStack implements io.gomint.inventory.item.ItemBowl {

    // CHECKSTYLE:OFF
    public ItemBowl( short data, int amount ) {
        super( 281, data, amount );
    }

    public ItemBowl( short data, int amount, NBTTagCompound nbt ) {
        super( 281, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BOWL;
    }

}