package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 103 )
 public class ItemMelonBlock extends ItemStack implements io.gomint.inventory.item.ItemMelonBlock {

    // CHECKSTYLE:OFF
    public ItemMelonBlock( short data, int amount ) {
        super( 103, data, amount );
    }

    public ItemMelonBlock( short data, int amount, NBTTagCompound nbt ) {
        super( 103, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.MELON_BLOCK;
    }

}