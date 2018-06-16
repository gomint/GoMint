package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 319 )
public class ItemRawPorkchop extends ItemFood implements io.gomint.inventory.item.ItemRawPorkchop {

    // CHECKSTYLE:OFF
    public ItemRawPorkchop( short data, int amount ) {
        super( 319, data, amount );
    }

    public ItemRawPorkchop( short data, int amount, NBTTagCompound nbt ) {
        super( 319, data, amount, nbt );
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
        return ItemType.RAW_PORKCHOP;
    }

}