package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 317 )
 public class ItemGoldenBoots extends ItemStack implements io.gomint.inventory.item.ItemGoldenBoots {

    // CHECKSTYLE:OFF
    public ItemGoldenBoots( short data, int amount ) {
        super( 317, data, amount );
    }

    public ItemGoldenBoots( short data, int amount, NBTTagCompound nbt ) {
        super( 317, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
