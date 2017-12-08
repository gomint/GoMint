package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 266 )
 public class ItemGoldIngot extends ItemStack implements io.gomint.inventory.item.ItemGoldIngot {

    // CHECKSTYLE:OFF
    public ItemGoldIngot( short data, int amount ) {
        super( 266, data, amount );
    }

    public ItemGoldIngot( short data, int amount, NBTTagCompound nbt ) {
        super( 266, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.GOLD_INGOT;
    }

}