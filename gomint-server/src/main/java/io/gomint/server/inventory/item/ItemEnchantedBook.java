package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 403 )
 public class ItemEnchantedBook extends ItemStack implements io.gomint.inventory.item.ItemEnchantedBook {

    // CHECKSTYLE:OFF
    public ItemEnchantedBook( short data, int amount ) {
        super( 403, data, amount );
    }

    public ItemEnchantedBook( short data, int amount, NBTTagCompound nbt ) {
        super( 403, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
