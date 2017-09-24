package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 129 )
 public class ItemEmeraldOre extends ItemStack implements io.gomint.inventory.item.ItemEmeraldOre {

    // CHECKSTYLE:OFF
    public ItemEmeraldOre( short data, int amount ) {
        super( 129, data, amount );
    }

    public ItemEmeraldOre( short data, int amount, NBTTagCompound nbt ) {
        super( 129, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
