package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 197 )
public class ItemDarkOakDoorBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemDarkOakDoorBlock( short data, int amount ) {
        super( 197, data, amount );
    }

    public ItemDarkOakDoorBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 197, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
