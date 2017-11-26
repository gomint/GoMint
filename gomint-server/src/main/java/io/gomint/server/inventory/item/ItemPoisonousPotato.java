package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;

import io.gomint.server.registry.RegisterInfo;
import io.gomint.taglib.NBTTagCompound;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 394 )
public class ItemPoisonousPotato extends ItemFood implements io.gomint.inventory.item.ItemPoisonousPotato {

    // CHECKSTYLE:OFF
    public ItemPoisonousPotato( short data, int amount ) {
        super( 394, data, amount );
    }

    public ItemPoisonousPotato( short data, int amount, NBTTagCompound nbt ) {
        super( 394, data, amount, nbt );
    }
    // CHECKSTYLE:ON

    @Override
    public float getSaturation() {
        return 0.3f;
    }

    @Override
    public float getHunger() {
        return 2;
    }

    @Override
    public ItemType getType() {
        return ItemType.POISONOUS_POTATO;
    }

}