package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 316 )
 public class ItemGoldenLeggings extends ItemStack implements io.gomint.inventory.item.ItemGoldenLeggings {

    // CHECKSTYLE:OFF
    public ItemGoldenLeggings( short data, int amount ) {
        super( 316, data, amount );
    }

    public ItemGoldenLeggings( short data, int amount, NBTTagCompound nbt ) {
        super( 316, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
