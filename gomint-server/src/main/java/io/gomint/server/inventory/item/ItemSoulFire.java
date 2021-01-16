package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 492, sId = "minecraft:soul_fire")
public class ItemSoulFire extends ItemStack< io.gomint.inventory.item.ItemSoulFire> implements io.gomint.inventory.item.ItemSoulFire {

    @Override
    public ItemType itemType() {
        return ItemType.SOUL_FIRE;
    }

}
