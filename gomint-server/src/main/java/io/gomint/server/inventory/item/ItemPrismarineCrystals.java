package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 422 )
 public class ItemPrismarineCrystals extends ItemStack implements io.gomint.inventory.item.ItemPrismarineCrystals {

    // CHECKSTYLE:OFF
    public ItemPrismarineCrystals( short data, int amount ) {
        super( 422, data, amount );
    }

    public ItemPrismarineCrystals( short data, int amount, NBTTagCompound nbt ) {
        super( 422, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
