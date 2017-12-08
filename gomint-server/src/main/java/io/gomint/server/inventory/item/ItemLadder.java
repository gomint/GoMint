package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 65 )
 public class ItemLadder extends ItemStack implements io.gomint.inventory.item.ItemLadder {

    // CHECKSTYLE:OFF
    public ItemLadder( short data, int amount ) {
        super( 65, data, amount );
    }

    public ItemLadder( short data, int amount, NBTTagCompound nbt ) {
        super( 65, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.LADDER;
    }

}