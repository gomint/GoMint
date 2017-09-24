package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 410 )
 public class ItemHopper extends ItemStack implements io.gomint.inventory.item.ItemHopper {

    // CHECKSTYLE:OFF
    public ItemHopper( short data, int amount ) {
        super( 410, data, amount );
    }

    public ItemHopper( short data, int amount, NBTTagCompound nbt ) {
        super( 410, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public int getBlockId() {
        return 154;
    }

}
