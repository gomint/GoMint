package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 195 )
public class ItemJungleDoorBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemJungleDoorBlock( short data, int amount ) {
        super( 195, data, amount );
    }

    public ItemJungleDoorBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 195, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
