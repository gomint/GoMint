package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 25 )
 public class ItemNoteBlock extends ItemStack implements io.gomint.inventory.item.ItemNoteBlock {

    // CHECKSTYLE:OFF
    public ItemNoteBlock( short data, int amount ) {
        super( 25, data, amount );
    }

    public ItemNoteBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 25, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.NOTE_BLOCK;
    }

}