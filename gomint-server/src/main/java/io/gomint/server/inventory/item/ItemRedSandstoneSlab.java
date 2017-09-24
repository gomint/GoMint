package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 182 )
 public class ItemRedSandstoneSlab extends ItemStack implements io.gomint.inventory.item.ItemRedSandstoneSlab {

    // CHECKSTYLE:OFF
    public ItemRedSandstoneSlab( short data, int amount ) {
        super( 182, data, amount );
    }

    public ItemRedSandstoneSlab( short data, int amount, NBTTagCompound nbt ) {
        super( 182, data, amount, nbt );
    }
    // CHECKSTYLE:ON

}
