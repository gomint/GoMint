package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 437 )
 public class ItemDragonBreath extends ItemStack implements io.gomint.inventory.item.ItemDragonBreath {

    // CHECKSTYLE:OFF
    public ItemDragonBreath( short data, int amount ) {
        super( 437, data, amount );
    }

    public ItemDragonBreath( short data, int amount, NBTTagCompound nbt ) {
        super( 437, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
