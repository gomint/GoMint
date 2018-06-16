package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 282 )
public class ItemMushroomStew extends ItemFood implements io.gomint.inventory.item.ItemMushroomStew {

    // CHECKSTYLE:OFF
    public ItemMushroomStew( short data, int amount ) {
        super( 282, data, amount );
    }

    public ItemMushroomStew( short data, int amount, NBTTagCompound nbt ) {
        super( 282, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public float getSaturation() {
        return 0.6f;
    }

    @Override
    public float getHunger() {
        return 6;
    }

    @Override
    public ItemType getType() {
        return ItemType.MUSHROOM_STEW;
    }

}