package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 380 )
public class ItemCauldron extends ItemStack implements io.gomint.inventory.item.ItemCauldron {

    // CHECKSTYLE:OFF
    public ItemCauldron( short data, int amount ) {
        super( 380, data, amount );
    }

    public ItemCauldron( short data, int amount, NBTTagCompound nbt ) {
        super( 380, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public int getBlockId() {
        return 118;
    }

}
