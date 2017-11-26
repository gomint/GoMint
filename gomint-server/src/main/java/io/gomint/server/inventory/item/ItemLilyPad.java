package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 111 )
 public class ItemLilyPad extends ItemStack implements io.gomint.inventory.item.ItemLilyPad {

    // CHECKSTYLE:OFF
    public ItemLilyPad( short data, int amount ) {
        super( 111, data, amount );
    }

    public ItemLilyPad( short data, int amount, NBTTagCompound nbt ) {
        super( 111, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.LILY_PAD;
    }

}