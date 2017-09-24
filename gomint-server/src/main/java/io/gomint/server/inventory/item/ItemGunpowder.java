package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 289 )
 public class ItemGunpowder extends ItemStack implements io.gomint.inventory.item.ItemGunpowder {

    // CHECKSTYLE:OFF
    public ItemGunpowder( short data, int amount ) {
        super( 289, data, amount );
    }

    public ItemGunpowder( short data, int amount, NBTTagCompound nbt ) {
        super( 289, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
