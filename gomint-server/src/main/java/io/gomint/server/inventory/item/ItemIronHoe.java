package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 292 )
 public class ItemIronHoe extends ItemStack implements io.gomint.inventory.item.ItemIronHoe {

    // CHECKSTYLE:OFF
    public ItemIronHoe( short data, int amount ) {
        super( 292, data, amount );
    }

    public ItemIronHoe( short data, int amount, NBTTagCompound nbt ) {
        super( 292, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
