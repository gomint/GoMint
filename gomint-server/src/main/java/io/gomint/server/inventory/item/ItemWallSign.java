package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 68 )
public class ItemWallSign extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemWallSign( short data, int amount ) {
        super( 68, data, amount );
    }

    public ItemWallSign( short data, int amount, NBTTagCompound nbt ) {
        super( 68, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
