package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 286 )
 public class ItemGoldenAxe extends ItemStack implements io.gomint.inventory.item.ItemGoldenAxe {

    // CHECKSTYLE:OFF
    public ItemGoldenAxe( short data, int amount ) {
        super( 286, data, amount );
    }

    public ItemGoldenAxe( short data, int amount, NBTTagCompound nbt ) {
        super( 286, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
