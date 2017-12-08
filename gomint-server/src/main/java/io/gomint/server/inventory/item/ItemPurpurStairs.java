package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 203 )
public class ItemPurpurStairs extends ItemStack implements io.gomint.inventory.item.ItemPurpurStairs {

    // CHECKSTYLE:OFF
    public ItemPurpurStairs( short data, int amount ) {
        super( 203, data, amount );
    }

    public ItemPurpurStairs( short data, int amount, NBTTagCompound nbt ) {
        super( 203, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.PURPUR_STAIRS;
    }

}
