package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 146 )
 public class ItemTrappedChest extends ItemStack implements io.gomint.inventory.item.ItemTrappedChest {

    // CHECKSTYLE:OFF
    public ItemTrappedChest( short data, int amount ) {
        super( 146, data, amount );
    }

    public ItemTrappedChest( short data, int amount, NBTTagCompound nbt ) {
        super( 146, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
