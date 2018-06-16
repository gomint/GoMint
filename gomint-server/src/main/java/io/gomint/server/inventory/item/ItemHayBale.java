package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 170 )
 public class ItemHayBale extends ItemStack implements io.gomint.inventory.item.ItemHayBale {

    // CHECKSTYLE:OFF
    public ItemHayBale( short data, int amount ) {
        super( 170, data, amount );
    }

    public ItemHayBale( short data, int amount, NBTTagCompound nbt ) {
        super( 170, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.HAY_BALE;
    }

}