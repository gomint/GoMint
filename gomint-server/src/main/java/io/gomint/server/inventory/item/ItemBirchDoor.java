package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 428 )
 public class ItemBirchDoor extends ItemStack implements io.gomint.inventory.item.ItemBirchDoor {

    // CHECKSTYLE:OFF
    public ItemBirchDoor( short data, int amount ) {
        super( 428, data, amount );
    }

    public ItemBirchDoor( short data, int amount, NBTTagCompound nbt ) {
        super( 428, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
