package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 210 ,sId = "minecraft:polished_blackstone" ) //TODO get ID??
public class ItemPolishedBlackStone extends ItemStack implements io.gomint.inventory.item.ItemPolishedBlackStone {

    @Override
    public String getBlockId() {
        return "minecraft:polished_blackstone";
    }

    @Override
    public ItemType getType() {
        return ItemType.POLISHED_BLACKSTONE;
    }
}
