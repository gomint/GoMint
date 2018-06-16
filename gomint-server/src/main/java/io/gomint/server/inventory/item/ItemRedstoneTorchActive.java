package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 76 )
 public class ItemRedstoneTorchActive extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemRedstoneTorchActive( short data, int amount ) {
        super( 76, data, amount );
    }

    public ItemRedstoneTorchActive( short data, int amount, NBTTagCompound nbt ) {
        super( 76, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.REDSTONE_TORCH_ACTIVE;
    }

}
