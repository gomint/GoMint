package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 163 )
 public class ItemAcaciaWoodStairs extends ItemStack implements io.gomint.inventory.item.ItemAcaciaWoodStairs {

    // CHECKSTYLE:OFF
    public ItemAcaciaWoodStairs( short data, int amount ) {
        super( 163, data, amount );
    }

    public ItemAcaciaWoodStairs( short data, int amount, NBTTagCompound nbt ) {
        super( 163, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.ACACIA_WOOD_STAIRS;
    }

}