package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( id = 393 )
public class ItemBakedPotato extends ItemFood implements io.gomint.inventory.item.ItemBakedPotato {

    @Override
    public float getSaturation() {
        return 0.6f;
    }

    @Override
    public float getHunger() {
        return 5;
    }

    @Override
    public ItemType getType() {
        return ItemType.BAKED_POTATO;
    }

}
