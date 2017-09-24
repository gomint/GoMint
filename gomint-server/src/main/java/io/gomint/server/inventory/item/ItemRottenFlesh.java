package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 367 )
 public class ItemRottenFlesh extends ItemStack implements io.gomint.inventory.item.ItemRottenFlesh {

    // CHECKSTYLE:OFF
    public ItemRottenFlesh( short data, int amount ) {
        super( 367, data, amount );
    }

    public ItemRottenFlesh( short data, int amount, NBTTagCompound nbt ) {
        super( 367, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
