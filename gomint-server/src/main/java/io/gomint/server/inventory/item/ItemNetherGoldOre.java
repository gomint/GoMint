package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 210 ,sId = "minecraft:nether_gold_ore" ) //TODO get ID??
public class ItemNetherGoldOre extends ItemStack implements io.gomint.inventory.item.ItemNetherGoldOre {

    @Override
    public String getBlockId() {
        return "minecraft:nether_gold_ore";
    }

    @Override
    public ItemType getType() {
        return ItemType.NETHER_GOLD_ORE;
    }
}
