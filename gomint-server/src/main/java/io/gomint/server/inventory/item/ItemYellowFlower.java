package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 37 )
 public class ItemYellowFlower extends ItemStack implements io.gomint.inventory.item.ItemYellowFlower {

    // CHECKSTYLE:OFF
    public ItemYellowFlower( short data, int amount ) {
        super( 37, data, amount );
    }

    public ItemYellowFlower( short data, int amount, NBTTagCompound nbt ) {
        super( 37, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
