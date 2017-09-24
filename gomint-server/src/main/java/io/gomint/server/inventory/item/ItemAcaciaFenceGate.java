package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 187 )
 public class ItemAcaciaFenceGate extends ItemStack implements io.gomint.inventory.item.ItemAcaciaFenceGate {

    // CHECKSTYLE:OFF
    public ItemAcaciaFenceGate( short data, int amount ) {
        super( 187, data, amount );
    }

    public ItemAcaciaFenceGate( short data, int amount, NBTTagCompound nbt ) {
        super( 187, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
