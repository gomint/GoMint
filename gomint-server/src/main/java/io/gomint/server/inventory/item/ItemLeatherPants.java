package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 300 )
 public class ItemLeatherPants extends ItemStack implements io.gomint.inventory.item.ItemLeatherPants {

    // CHECKSTYLE:OFF
    public ItemLeatherPants( short data, int amount ) {
        super( 300, data, amount );
    }

    public ItemLeatherPants( short data, int amount, NBTTagCompound nbt ) {
        super( 300, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
