package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 371 )
 public class ItemGoldNugget extends ItemStack implements io.gomint.inventory.item.ItemGoldNugget {

    // CHECKSTYLE:OFF
    public ItemGoldNugget( short data, int amount ) {
        super( 371, data, amount );
    }

    public ItemGoldNugget( short data, int amount, NBTTagCompound nbt ) {
        super( 371, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.GOLD_NUGGET;
    }

}