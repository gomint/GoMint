package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 73 )
 public class ItemRedstoneOre extends ItemStack implements io.gomint.inventory.item.ItemRedstoneOre {

    // CHECKSTYLE:OFF
    public ItemRedstoneOre( short data, int amount ) {
        super( 73, data, amount );
    }

    public ItemRedstoneOre( short data, int amount, NBTTagCompound nbt ) {
        super( 73, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.REDSTONE_ORE;
    }

}