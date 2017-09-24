package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 186 )
 public class ItemDarkOakFenceGate extends ItemStack implements io.gomint.inventory.item.ItemDarkOakFenceGate {

    // CHECKSTYLE:OFF
    public ItemDarkOakFenceGate( short data, int amount ) {
        super( 186, data, amount );
    }

    public ItemDarkOakFenceGate( short data, int amount, NBTTagCompound nbt ) {
        super( 186, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
