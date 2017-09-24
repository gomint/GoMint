package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 194 )
public class ItemBirchDoorBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemBirchDoorBlock( short data, int amount ) {
        super( 194, data, amount );
    }

    public ItemBirchDoorBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 194, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
