package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo( sId = "minecraft:baked_potato", id = 393 )
public class ItemBakedPotato extends ItemFood<io.gomint.inventory.item.ItemBakedPotato> implements io.gomint.inventory.item.ItemBakedPotato {

    @Override
    public float getSaturation() {
        return 0.6f;
    }

    @Override
    public float getHunger() {
        return 5;
    }

    @Override
    public ItemType itemType() {
        return ItemType.BAKED_POTATO;
    }

}
