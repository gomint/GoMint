package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 118 )
 public class ItemCauldronBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemCauldronBlock( short data, int amount ) {
        super( 118, data, amount );
    }

    public ItemCauldronBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 118, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
