package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 161 )
 public class ItemAcaciaLeaves extends ItemStack implements io.gomint.inventory.item.ItemAcaciaLeaves {

    // CHECKSTYLE:OFF
    public ItemAcaciaLeaves( short data, int amount ) {
        super( 161, data, amount );
    }

    public ItemAcaciaLeaves( short data, int amount, NBTTagCompound nbt ) {
        super( 161, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
