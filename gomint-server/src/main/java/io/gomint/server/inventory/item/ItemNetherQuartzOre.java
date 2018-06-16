package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 153 )
 public class ItemNetherQuartzOre extends ItemStack implements io.gomint.inventory.item.ItemNetherQuartzOre {

    // CHECKSTYLE:OFF
    public ItemNetherQuartzOre( short data, int amount ) {
        super( 153, data, amount );
    }

    public ItemNetherQuartzOre( short data, int amount, NBTTagCompound nbt ) {
        super( 153, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.NETHER_QUARTZ_ORE;
    }

}