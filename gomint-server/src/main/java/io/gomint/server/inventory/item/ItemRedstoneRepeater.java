package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 356 )
public class ItemRedstoneRepeater extends ItemStack implements io.gomint.inventory.item.ItemRedstoneRepeater {

    // CHECKSTYLE:OFF
    public ItemRedstoneRepeater( short data, int amount ) {
        super( 356, data, amount );
    }

    public ItemRedstoneRepeater( short data, int amount, NBTTagCompound nbt ) {
        super( 356, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.REDSTONE_REPEATER;
    }

}
