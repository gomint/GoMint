package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 301 )
 public class ItemLeatherBoots extends ItemStack implements io.gomint.inventory.item.ItemLeatherBoots {

    // CHECKSTYLE:OFF
    public ItemLeatherBoots( short data, int amount ) {
        super( 301, data, amount );
    }

    public ItemLeatherBoots( short data, int amount, NBTTagCompound nbt ) {
        super( 301, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
