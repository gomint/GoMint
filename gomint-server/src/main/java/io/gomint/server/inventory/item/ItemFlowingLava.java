package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 10 )
 public class ItemFlowingLava extends ItemStack implements io.gomint.inventory.item.ItemFlowingLava {

    // CHECKSTYLE:OFF
    public ItemFlowingLava( short data, int amount ) {
        super( 10, data, amount );
    }

    public ItemFlowingLava( short data, int amount, NBTTagCompound nbt ) {
        super( 10, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.FLOWING_LAVA;
    }

}