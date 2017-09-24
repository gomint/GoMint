package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 409 )
 public class ItemPrismarineShard extends ItemStack implements io.gomint.inventory.item.ItemPrismarineShard {

    // CHECKSTYLE:OFF
    public ItemPrismarineShard( short data, int amount ) {
        super( 409, data, amount );
    }

    public ItemPrismarineShard( short data, int amount, NBTTagCompound nbt ) {
        super( 409, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
