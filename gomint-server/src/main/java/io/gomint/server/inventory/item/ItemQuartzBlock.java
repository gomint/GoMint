package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 155 )
 public class ItemQuartzBlock extends ItemStack implements io.gomint.inventory.item.ItemQuartzBlock {

    // CHECKSTYLE:OFF
    public ItemQuartzBlock( short data, int amount ) {
        super( 155, data, amount );
    }

    public ItemQuartzBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 155, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
