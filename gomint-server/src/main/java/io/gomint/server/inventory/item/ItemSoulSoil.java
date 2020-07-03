package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 491, sId = "minecraft:soul_soil")
public class ItemSoulSoil extends ItemStack implements io.gomint.inventory.item.ItemSoulSoil {

    @Override
    public String getBlockId() {
        return "minecraft:soul_soil";
    }

    @Override
    public ItemType getType() {
        return ItemType.SOUL_SOIL;
    }
}
