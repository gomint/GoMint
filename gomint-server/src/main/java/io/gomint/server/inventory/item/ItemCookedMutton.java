package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 424 )
public class ItemCookedMutton extends ItemFood implements io.gomint.inventory.item.ItemCookedMutton {

    // CHECKSTYLE:OFF
    public ItemCookedMutton( short data, int amount ) {
        super( 424, data, amount );
    }

    public ItemCookedMutton( short data, int amount, NBTTagCompound nbt ) {
        super( 424, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public float getSaturation() {
        return 0.8f;
    }

    @Override
    public float getHunger() {
        return 6;
    }

    @Override
    public ItemType getType() {
        return ItemType.COOKED_MUTTON;
    }

}