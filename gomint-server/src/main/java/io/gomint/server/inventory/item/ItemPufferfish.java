package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 462 )
public class ItemPufferfish extends ItemFood implements io.gomint.inventory.item.ItemPufferfish {

    // CHECKSTYLE:OFF
    public ItemPufferfish( short data, int amount ) {
        super( 462, data, amount );
    }

    public ItemPufferfish( short data, int amount, NBTTagCompound nbt ) {
        super( 462, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public float getSaturation() {
        return 0.1f;
    }

    @Override
    public float getHunger() {
        return 1;
    }

    @Override
    public ItemType getType() {
        return ItemType.PUFFERFISH;
    }

}