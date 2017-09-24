package io.gomint.server.inventory.item;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 354 )
public class ItemCake extends ItemStack implements io.gomint.inventory.item.ItemCake {

    // CHECKSTYLE:OFF
    public ItemCake( short data, int amount ) {
        super( 354, data, amount );
    }

    public ItemCake( short data, int amount, NBTTagCompound nbt ) {
        super( 354, data, amount, nbt );
    }
    // CHECKSTYLE:ON


    @Override
    public int getBlockId() {
        return 92;
    }

}
