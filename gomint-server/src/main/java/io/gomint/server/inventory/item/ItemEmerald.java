package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 388 )
 public class ItemEmerald extends ItemStack implements io.gomint.inventory.item.ItemEmerald {

    // CHECKSTYLE:OFF
    public ItemEmerald( short data, int amount ) {
        super( 388, data, amount );
    }

    public ItemEmerald( short data, int amount, NBTTagCompound nbt ) {
        super( 388, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
