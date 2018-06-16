package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 75 )
 public class ItemRedstoneTorchInactive extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemRedstoneTorchInactive( short data, int amount ) {
        super( 75, data, amount );
    }

    public ItemRedstoneTorchInactive( short data, int amount, NBTTagCompound nbt ) {
        super( 75, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.REDSTONE_TORCH_INACTIVE;
    }

}
