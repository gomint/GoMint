package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 185 )
 public class ItemJungleWoodFenceGate extends ItemStack implements io.gomint.inventory.item.ItemJungleWoodFenceGate {

    // CHECKSTYLE:OFF
    public ItemJungleWoodFenceGate( short data, int amount ) {
        super( 185, data, amount );
    }

    public ItemJungleWoodFenceGate( short data, int amount, NBTTagCompound nbt ) {
        super( 185, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.JUNGLE_FENCE_GATE;
    }

}
