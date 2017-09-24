package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 19 )
 public class ItemSponge extends ItemStack implements io.gomint.inventory.item.ItemSponge {

    // CHECKSTYLE:OFF
    public ItemSponge( short data, int amount ) {
        super( 19, data, amount );
    }

    public ItemSponge( short data, int amount, NBTTagCompound nbt ) {
        super( 19, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
