package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 544 ,sId = "minecraft:crying_obsidian" )
public class ItemCryingObsidian extends ItemStack implements io.gomint.inventory.item.ItemCryingObsidian {

    @Override
    public String getBlockId() {
        return "minecraft:crying_obsidian";
    }

    @Override
    public ItemType getType() {
        return ItemType.CRYING_OBSIDIAN;
    }
}
