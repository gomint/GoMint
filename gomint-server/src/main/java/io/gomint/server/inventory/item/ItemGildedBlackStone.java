package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 210 ,sId = "minecraft:gilded_blackstone" ) //TODO get ID??
public class ItemGildedBlackStone extends ItemStack implements io.gomint.inventory.item.ItemGildedBlackStone {

    @Override
    public String getBlockId() {
        return "minecraft:gilded_blackstone";
    }

    @Override
    public ItemType getType() {
        return ItemType.GILDED_BLACKSTONE;
    }
}
