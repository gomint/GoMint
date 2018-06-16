package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 142 )
 public class ItemPotatoBlock extends ItemStack {

    // CHECKSTYLE:OFF
    public ItemPotatoBlock( short data, int amount ) {
        super( 142, data, amount );
    }

    public ItemPotatoBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 142, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.POTATO_BLOCK;
    }

}