package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 391 )
public class ItemCarrot extends ItemFood implements io.gomint.inventory.item.ItemCarrot {

    // CHECKSTYLE:OFF
    public ItemCarrot( short data, int amount ) {
        super( 391, data, amount );
    }

    public ItemCarrot( short data, int amount, NBTTagCompound nbt ) {
        super( 391, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public ItemType getType() {
        return ItemType.CARROT;
    }

    @Override
    public float getSaturation() {
        return 0.6f;
    }

    @Override
    public float getHunger() {
        return 3;
    }

}
