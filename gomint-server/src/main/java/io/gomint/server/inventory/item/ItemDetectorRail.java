package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 28 )
 public class ItemDetectorRail extends ItemStack implements io.gomint.inventory.item.ItemDetectorRail {

    // CHECKSTYLE:OFF
    public ItemDetectorRail( short data, int amount ) {
        super( 28, data, amount );
    }

    public ItemDetectorRail( short data, int amount, NBTTagCompound nbt ) {
        super( 28, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
