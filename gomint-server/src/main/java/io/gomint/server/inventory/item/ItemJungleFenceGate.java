package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 185 )
 public class ItemJungleFenceGate extends ItemStack implements io.gomint.inventory.item.ItemJungleFenceGate {

    // CHECKSTYLE:OFF
    public ItemJungleFenceGate( short data, int amount ) {
        super( 185, data, amount );
    }

    public ItemJungleFenceGate( short data, int amount, NBTTagCompound nbt ) {
        super( 185, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
