package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 260 )
public class ItemHoneyBottle extends ItemFood {


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
        return 1.2f; //TODO KEine ahnung
    }

    @Override
    public float getHunger() {
        return 6;
    }
}
