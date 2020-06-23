package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 210 ,sId = "minecraft:ancient_debris" ) //TODO get ID ???
public class ItemAncientDebris extends ItemStack implements io.gomint.inventory.item.ItemAncientDebris {

    @Override
    public String getBlockId() {
        return "minecraft:ancient_debris";
    }

    @Override
    public ItemType getType() {
        return ItemType.ANCIENT_DEBRIS;
    }
}
