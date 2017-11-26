package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 364 )
public class ItemCookedBeef extends ItemFood implements io.gomint.inventory.item.ItemCookedBeef {

    // CHECKSTYLE:OFF
    public ItemCookedBeef( short data, int amount ) {
        super( 364, data, amount );
    }

    public ItemCookedBeef( short data, int amount, NBTTagCompound nbt ) {
        super( 364, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public float getSaturation() {
        return 0.8f;
    }

    @Override
    public float getHunger() {
        return 8f;
    }

    @Override
    public ItemType getType() {
        return ItemType.COOKED_BEEF;
    }

}