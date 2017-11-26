package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 141 )
 public class ItemCarrots extends ItemStack implements io.gomint.inventory.item.ItemCarrots {

    // CHECKSTYLE:OFF
    public ItemCarrots( short data, int amount ) {
        super( 141, data, amount );
    }

    public ItemCarrots( short data, int amount, NBTTagCompound nbt ) {
        super( 141, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.CARROTS;
    }

}