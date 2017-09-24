package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 337 )
 public class ItemClayBall extends ItemStack implements io.gomint.inventory.item.ItemClayBall {

    // CHECKSTYLE:OFF
    public ItemClayBall( short data, int amount ) {
        super( 337, data, amount );
    }

    public ItemClayBall( short data, int amount, NBTTagCompound nbt ) {
        super( 337, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
