package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 338 )
public class ItemReeds extends ItemStack implements io.gomint.inventory.item.ItemReeds {

    // CHECKSTYLE:OFF
    public ItemReeds( short data, int amount ) {
        super( 338, data, amount );
    }

    public ItemReeds( short data, int amount, NBTTagCompound nbt ) {
        super( 338, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.REEDS;
    }

}
