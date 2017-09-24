package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 150 )
 public class ItemPoweredComparator extends ItemStack implements io.gomint.inventory.item.ItemPoweredComparator {

    // CHECKSTYLE:OFF
    public ItemPoweredComparator( short data, int amount ) {
        super( 150, data, amount );
    }

    public ItemPoweredComparator( short data, int amount, NBTTagCompound nbt ) {
        super( 150, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
