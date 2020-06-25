package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 494, sId = "minecraft:target")
public class ItemTarget extends ItemStack implements io.gomint.inventory.item.ItemTarget {

    @Override
    public String getBlockId() {
        return "minecraft:target";
    }

    @Override
    public ItemType getType() {
        return ItemType.SOUL_CAMPFIRE;
    }
}
