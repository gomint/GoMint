package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 260 )
public class ItemHoneyBottle extends ItemFood implements io.gomint.inventory.item.ItemHoneyBottle {


    @Override
    public byte getMaximumAmount() {
        return 16;
    }

    @Override
    public ItemType getType() {
        return ItemType.HONEY_BOTTLE;
    }

    @Override
    public float getSaturation() {
        return (float) 1.2;
    }

    @Override
    public float getHunger() {
        return 6;
    }
}
