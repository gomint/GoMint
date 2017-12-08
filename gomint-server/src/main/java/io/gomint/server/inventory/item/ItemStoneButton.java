package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 77 )
 public class ItemStoneButton extends ItemStack implements io.gomint.inventory.item.ItemStoneButton {

    // CHECKSTYLE:OFF
    public ItemStoneButton( short data, int amount ) {
        super( 77, data, amount );
    }

    public ItemStoneButton( short data, int amount, NBTTagCompound nbt ) {
        super( 77, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.STONE_BUTTON;
    }

}