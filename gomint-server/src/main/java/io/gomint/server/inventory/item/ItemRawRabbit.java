package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 411 )
 public class ItemRawRabbit extends ItemStack implements io.gomint.inventory.item.ItemRawRabbit {

    // CHECKSTYLE:OFF
    public ItemRawRabbit( short data, int amount ) {
        super( 411, data, amount );
    }

    public ItemRawRabbit( short data, int amount, NBTTagCompound nbt ) {
        super( 411, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
