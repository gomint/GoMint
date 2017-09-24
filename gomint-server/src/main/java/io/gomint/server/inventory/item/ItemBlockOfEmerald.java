package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 133 )
 public class ItemBlockOfEmerald extends ItemStack implements io.gomint.inventory.item.ItemBlockOfEmerald {

    // CHECKSTYLE:OFF
    public ItemBlockOfEmerald( short data, int amount ) {
        super( 133, data, amount );
    }

    public ItemBlockOfEmerald( short data, int amount, NBTTagCompound nbt ) {
        super( 133, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
