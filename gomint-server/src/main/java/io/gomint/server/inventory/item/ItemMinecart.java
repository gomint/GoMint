package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 328 )
 public class ItemMinecart extends ItemStack implements io.gomint.inventory.item.ItemMinecart {

    // CHECKSTYLE:OFF
    public ItemMinecart( short data, int amount ) {
        super( 328, data, amount );
    }

    public ItemMinecart( short data, int amount, NBTTagCompound nbt ) {
        super( 328, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
