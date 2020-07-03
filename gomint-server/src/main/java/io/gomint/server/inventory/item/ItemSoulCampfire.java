package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 545, sId = "minecraft:soul_campfire")
public class ItemSoulCampfire extends ItemStack implements io.gomint.inventory.item.ItemSoulCampfire {

    @Override
    public byte getMaximumAmount() {
        return 1;
    }

    @Override
    public String getBlockId() {
        return "minecraft:soul_campfire";
    }

    @Override
    public ItemType getType() {
        return ItemType.SOUL_CAMPFIRE;
    }
}
