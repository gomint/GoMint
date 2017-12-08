package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 186 )
 public class ItemDarkOakWoodFenceGate extends ItemStack implements io.gomint.inventory.item.ItemDarkOakWoodFenceGate {

    // CHECKSTYLE:OFF
    public ItemDarkOakWoodFenceGate( short data, int amount ) {
        super( 186, data, amount );
    }

    public ItemDarkOakWoodFenceGate( short data, int amount, NBTTagCompound nbt ) {
        super( 186, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.DARK_OAK_FENCE_GATE;
    }

}
