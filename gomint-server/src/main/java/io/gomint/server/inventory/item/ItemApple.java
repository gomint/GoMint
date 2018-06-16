package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 260 )
public class ItemApple extends ItemFood implements io.gomint.inventory.item.ItemApple {

    // CHECKSTYLE:OFF
    public ItemApple( short data, int amount ) {
        super( 260, data, amount );
    }

    public ItemApple( short data, int amount, NBTTagCompound nbt ) {
        super( 260, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public float getSaturation() {
        return 0.3f;
    }

    @Override
    public float getHunger() {
        return 4;
    }

    @Override
    public ItemType getType() {
        return ItemType.APPLE;
    }

}