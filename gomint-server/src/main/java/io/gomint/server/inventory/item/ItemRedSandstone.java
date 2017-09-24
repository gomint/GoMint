package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 179 )
 public class ItemRedSandstone extends ItemStack implements io.gomint.inventory.item.ItemRedSandstone {

    // CHECKSTYLE:OFF
    public ItemRedSandstone( short data, int amount ) {
        super( 179, data, amount );
    }

    public ItemRedSandstone( short data, int amount, NBTTagCompound nbt ) {
        super( 179, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
