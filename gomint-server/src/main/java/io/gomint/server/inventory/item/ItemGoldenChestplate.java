package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 315 )
 public class ItemGoldenChestplate extends ItemStack implements io.gomint.inventory.item.ItemGoldenChestplate {

    // CHECKSTYLE:OFF
    public ItemGoldenChestplate( short data, int amount ) {
        super( 315, data, amount );
    }

    public ItemGoldenChestplate( short data, int amount, NBTTagCompound nbt ) {
        super( 315, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
