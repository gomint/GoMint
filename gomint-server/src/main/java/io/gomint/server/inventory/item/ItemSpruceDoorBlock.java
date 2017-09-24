package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 193 )
public class ItemSpruceDoorBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemSpruceDoorBlock( short data, int amount ) {
        super( 193, data, amount );
    }

    public ItemSpruceDoorBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 193, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
