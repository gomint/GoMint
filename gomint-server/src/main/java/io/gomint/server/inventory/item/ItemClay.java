package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 82 )
public class ItemClay extends ItemStack implements io.gomint.inventory.item.ItemClay {

    // CHECKSTYLE:OFF
    public ItemClay( short data, int amount ) {
        super( 82, data, amount );
    }

    public ItemClay( short data, int amount, NBTTagCompound nbt ) {
        super( 82, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.CLAY;
    }

}
