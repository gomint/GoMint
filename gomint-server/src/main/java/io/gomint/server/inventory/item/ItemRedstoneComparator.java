package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 404 )
public class ItemRedstoneComparator extends ItemStack implements io.gomint.inventory.item.ItemRedstoneComparator {

    // CHECKSTYLE:OFF
    public ItemRedstoneComparator( short data, int amount ) {
        super( 404, data, amount );
    }

    public ItemRedstoneComparator( short data, int amount, NBTTagCompound nbt ) {
        super( 404, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.COMPARATOR;
    }

}
