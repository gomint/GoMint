package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 151 )
 public class ItemDaylightDetector extends ItemStack implements io.gomint.inventory.item.ItemDaylightDetector {

    // CHECKSTYLE:OFF
    public ItemDaylightDetector( short data, int amount ) {
        super( 151, data, amount );
    }

    public ItemDaylightDetector( short data, int amount, NBTTagCompound nbt ) {
        super( 151, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
