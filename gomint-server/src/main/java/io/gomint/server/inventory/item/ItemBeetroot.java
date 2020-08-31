package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:beetroot", id = 457, def = true)
@RegisterInfo(sId = "minecraft:item.beetroot", id = 244)
public class ItemBeetroot extends ItemFood implements io.gomint.inventory.item.ItemBeetroot {

    @Override
    public float getSaturation() {
        return 0.6f;
    }

    @Override
    public float getHunger() {
        return 1;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.BEETROOT;
    }

}
