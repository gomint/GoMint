package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 39 )
 public class ItemBrownMushroom extends ItemStack implements io.gomint.inventory.item.ItemBrownMushroom {

    // CHECKSTYLE:OFF
    public ItemBrownMushroom( short data, int amount ) {
        super( 39, data, amount );
    }

    public ItemBrownMushroom( short data, int amount, NBTTagCompound nbt ) {
        super( 39, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.BROWN_MUSHROOM;
    }

}