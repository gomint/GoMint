package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 183 )
 public class ItemSpruceFenceGate extends ItemStack implements io.gomint.inventory.item.ItemSpruceFenceGate {

    // CHECKSTYLE:OFF
    public ItemSpruceFenceGate( short data, int amount ) {
        super( 183, data, amount );
    }

    public ItemSpruceFenceGate( short data, int amount, NBTTagCompound nbt ) {
        super( 183, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.SPRUCE_FENCE_GATE;
    }

}