package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 117 )
 public class ItemBrewingStandBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemBrewingStandBlock( short data, int amount ) {
        super( 117, data, amount );
    }

    public ItemBrewingStandBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 117, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
