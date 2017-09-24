package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 184 )
 public class ItemBirchFenceGate extends ItemStack implements io.gomint.inventory.item.ItemBirchFenceGate {

    // CHECKSTYLE:OFF
    public ItemBirchFenceGate( short data, int amount ) {
        super( 184, data, amount );
    }

    public ItemBirchFenceGate( short data, int amount, NBTTagCompound nbt ) {
        super( 184, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
