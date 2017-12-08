package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 149 )
public class ItemRedstoneComparatorUnpowered extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemRedstoneComparatorUnpowered( short data, int amount ) {
        super( 149, data, amount );
    }

    public ItemRedstoneComparatorUnpowered( short data, int amount, NBTTagCompound nbt ) {
        super( 149, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.REDSTONE_COMPARATOR_UNPOWERED;
    }

}
