package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 17 )
public class ItemLog extends ItemStack implements io.gomint.inventory.item.ItemLog {

    // CHECKSTYLE:OFF
    public ItemLog( short data, int amount ) {
        super( 17, data, amount );
    }

    public ItemLog( short data, int amount, NBTTagCompound nbt ) {
        super( 17, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.WOOD;
    }

}
