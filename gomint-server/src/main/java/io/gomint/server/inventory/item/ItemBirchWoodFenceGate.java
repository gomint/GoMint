package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 184 )
 public class ItemBirchWoodFenceGate extends ItemStack implements io.gomint.inventory.item.ItemBirchWoodFenceGate {

    // CHECKSTYLE:OFF
    public ItemBirchWoodFenceGate( short data, int amount ) {
        super( 184, data, amount );
    }

    public ItemBirchWoodFenceGate( short data, int amount, NBTTagCompound nbt ) {
        super( 184, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BIRCH_FENCE_GATE;
    }

}
