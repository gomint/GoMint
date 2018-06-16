package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 156 )
 public class ItemQuartzStairs extends ItemStack implements io.gomint.inventory.item.ItemQuartzStairs {

    // CHECKSTYLE:OFF
    public ItemQuartzStairs( short data, int amount ) {
        super( 156, data, amount );
    }

    public ItemQuartzStairs( short data, int amount, NBTTagCompound nbt ) {
        super( 156, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.QUARTZ_STAIRS;
    }

}