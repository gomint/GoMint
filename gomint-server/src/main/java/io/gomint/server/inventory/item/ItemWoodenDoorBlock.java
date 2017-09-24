package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 64 )
public class ItemWoodenDoorBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemWoodenDoorBlock( short data, int amount ) {
        super( 64, data, amount );
    }

    public ItemWoodenDoorBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 64, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
