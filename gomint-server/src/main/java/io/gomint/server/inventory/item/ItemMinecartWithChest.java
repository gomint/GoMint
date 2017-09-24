package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 342 )
 public class ItemMinecartWithChest extends ItemStack implements io.gomint.inventory.item.ItemMinecartWithChest {

    // CHECKSTYLE:OFF
    public ItemMinecartWithChest( short data, int amount ) {
        super( 342, data, amount );
    }

    public ItemMinecartWithChest( short data, int amount, NBTTagCompound nbt ) {
        super( 342, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
