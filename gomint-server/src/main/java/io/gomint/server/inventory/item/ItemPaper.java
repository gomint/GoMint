package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 339 )
 public class ItemPaper extends ItemStack implements io.gomint.inventory.item.ItemPaper {

    // CHECKSTYLE:OFF
    public ItemPaper( short data, int amount ) {
        super( 339, data, amount );
    }

    public ItemPaper( short data, int amount, NBTTagCompound nbt ) {
        super( 339, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.PAPER;
    }

}