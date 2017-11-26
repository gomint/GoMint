package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 50 )
 public class ItemTorch extends ItemStack implements io.gomint.inventory.item.ItemTorch {

    // CHECKSTYLE:OFF
    public ItemTorch( short data, int amount ) {
        super( 50, data, amount );
    }

    public ItemTorch( short data, int amount, NBTTagCompound nbt ) {
        super( 50, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.TORCH;
    }

}