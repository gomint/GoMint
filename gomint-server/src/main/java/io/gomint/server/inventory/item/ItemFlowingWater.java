package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 8 )
 public class ItemFlowingWater extends ItemStack implements io.gomint.inventory.item.ItemFlowingWater {

    // CHECKSTYLE:OFF
    public ItemFlowingWater( short data, int amount ) {
        super( 8, data, amount );
    }

    public ItemFlowingWater( short data, int amount, NBTTagCompound nbt ) {
        super( 8, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.FLOWING_WATER;
    }

}