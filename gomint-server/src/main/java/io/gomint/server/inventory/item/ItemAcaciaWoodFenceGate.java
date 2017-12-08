package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 187 )
public class ItemAcaciaWoodFenceGate extends ItemStack implements io.gomint.inventory.item.ItemAcaciaWoodFenceGate {

    // CHECKSTYLE:OFF
    public ItemAcaciaWoodFenceGate( short data, int amount ) {
        super( 187, data, amount );
    }

    public ItemAcaciaWoodFenceGate( short data, int amount, NBTTagCompound nbt ) {
        super( 187, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.ACACIA_FENCE_GATE;
    }

}
