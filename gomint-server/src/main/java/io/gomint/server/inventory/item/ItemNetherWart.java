package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 115 )
 public class ItemNetherWart extends ItemStack implements io.gomint.inventory.item.ItemNetherWart {

    // CHECKSTYLE:OFF
    public ItemNetherWart( short data, int amount ) {
        super( 115, data, amount );
    }

    public ItemNetherWart( short data, int amount, NBTTagCompound nbt ) {
        super( 115, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.NETHER_WART;
    }

}