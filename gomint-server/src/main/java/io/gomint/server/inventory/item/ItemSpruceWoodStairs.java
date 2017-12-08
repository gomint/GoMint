package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 134 )
 public class ItemSpruceWoodStairs extends ItemStack implements io.gomint.inventory.item.ItemSpruceWoodStairs {

    // CHECKSTYLE:OFF
    public ItemSpruceWoodStairs( short data, int amount ) {
        super( 134, data, amount );
    }

    public ItemSpruceWoodStairs( short data, int amount, NBTTagCompound nbt ) {
        super( 134, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.SPRUCE_WOOD_STAIRS;
    }

}