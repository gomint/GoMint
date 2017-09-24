package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 94 )
 public class ItemPoweredRepeater extends ItemStack implements io.gomint.inventory.item.ItemPoweredRepeater {

    // CHECKSTYLE:OFF
    public ItemPoweredRepeater( short data, int amount ) {
        super( 94, data, amount );
    }

    public ItemPoweredRepeater( short data, int amount, NBTTagCompound nbt ) {
        super( 94, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
