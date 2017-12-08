package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 255 )
 public class ItemReserved6 extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemReserved6( short data, int amount ) {
        super( 255, data, amount );
    }

    public ItemReserved6( short data, int amount, NBTTagCompound nbt ) {
        super( 255, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.RESERVED6;
    }

}
