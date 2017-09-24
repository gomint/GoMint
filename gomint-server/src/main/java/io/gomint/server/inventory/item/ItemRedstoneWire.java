package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 55 )
 public class ItemRedstoneWire extends ItemStack implements io.gomint.inventory.item.ItemRedstoneWire {

    // CHECKSTYLE:OFF
    public ItemRedstoneWire( short data, int amount ) {
        super( 55, data, amount );
    }

    public ItemRedstoneWire( short data, int amount, NBTTagCompound nbt ) {
        super( 55, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
