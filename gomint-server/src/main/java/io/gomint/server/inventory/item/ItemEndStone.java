package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 121 )
 public class ItemEndStone extends ItemStack implements io.gomint.inventory.item.ItemEndStone {

    // CHECKSTYLE:OFF
    public ItemEndStone( short data, int amount ) {
        super( 121, data, amount );
    }

    public ItemEndStone( short data, int amount, NBTTagCompound nbt ) {
        super( 121, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.END_STONE;
    }

}