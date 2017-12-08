package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 363 )
public class ItemRawBeef extends ItemFood implements io.gomint.inventory.item.ItemRawBeef {

    // CHECKSTYLE:OFF
    public ItemRawBeef( short data, int amount ) {
        super( 363, data, amount );
    }

    public ItemRawBeef( short data, int amount, NBTTagCompound nbt ) {
        super( 363, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public float getSaturation() {
        return 0.3f;
    }

    @Override
    public float getHunger() {
        return 3;
    }

    @Override
    public ItemType getType() {
        return ItemType.RAW_BEEF;
    }

}