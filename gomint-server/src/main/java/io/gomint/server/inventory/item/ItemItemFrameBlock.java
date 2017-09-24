package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 199 )
 public class ItemItemFrameBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemItemFrameBlock( short data, int amount ) {
        super( 199, data, amount );
    }

    public ItemItemFrameBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 199, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
