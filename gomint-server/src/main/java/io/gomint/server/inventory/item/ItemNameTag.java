package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 421 )
 public class ItemNameTag extends ItemStack implements io.gomint.inventory.item.ItemNameTag {

    // CHECKSTYLE:OFF
    public ItemNameTag( short data, int amount ) {
        super( 421, data, amount );
    }

    public ItemNameTag( short data, int amount, NBTTagCompound nbt ) {
        super( 421, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.NAME_TAG;
    }

}