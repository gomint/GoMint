package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 287 )
public class ItemString extends ItemStack implements io.gomint.inventory.item.ItemString {

    // CHECKSTYLE:OFF
    public ItemString( short data, int amount ) {
        super( 287, data, amount );
    }

    public ItemString( short data, int amount, NBTTagCompound nbt ) {
        super( 287, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.STRING;
    }

}