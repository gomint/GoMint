package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 173 )
 public class ItemBlockOfCoal extends ItemStack implements io.gomint.inventory.item.ItemBlockOfCoal {

    // CHECKSTYLE:OFF
    public ItemBlockOfCoal( short data, int amount ) {
        super( 173, data, amount );
    }

    public ItemBlockOfCoal( short data, int amount, NBTTagCompound nbt ) {
        super( 173, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BLOCK_OF_COAL;
    }

}