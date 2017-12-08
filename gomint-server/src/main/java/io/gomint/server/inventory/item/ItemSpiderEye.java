package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 375 )
public class ItemSpiderEye extends ItemFood implements io.gomint.inventory.item.ItemSpiderEye {

    // CHECKSTYLE:OFF
    public ItemSpiderEye( short data, int amount ) {
        super( 375, data, amount );
    }

    public ItemSpiderEye( short data, int amount, NBTTagCompound nbt ) {
        super( 375, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public float getSaturation() {
        return 0.8f;
    }

    @Override
    public float getHunger() {
        return 2;
    }

    @Override
    public ItemType getType() {
        return ItemType.SPIDER_EYE;
    }

}