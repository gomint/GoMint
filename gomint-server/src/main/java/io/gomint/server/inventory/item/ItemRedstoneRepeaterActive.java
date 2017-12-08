package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 94 )
public class ItemRedstoneRepeaterActive extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemRedstoneRepeaterActive( short data, int amount ) {
        super( 94, data, amount );
    }

    public ItemRedstoneRepeaterActive( short data, int amount, NBTTagCompound nbt ) {
        super( 94, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.REDSTONE_REPEATER_ACTIVE;
    }

}
