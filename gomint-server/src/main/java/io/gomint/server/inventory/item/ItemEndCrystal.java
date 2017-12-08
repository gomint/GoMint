package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 426 )
 public class ItemEndCrystal extends ItemStack implements io.gomint.inventory.item.ItemEndCrystal {

    // CHECKSTYLE:OFF
    public ItemEndCrystal( short data, int amount ) {
        super( 426, data, amount );
    }

    public ItemEndCrystal( short data, int amount, NBTTagCompound nbt ) {
        super( 426, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.END_CRYSTAL;
    }

}