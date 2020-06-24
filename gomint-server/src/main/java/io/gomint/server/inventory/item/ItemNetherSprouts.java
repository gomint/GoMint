package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 210 ,sId = "minecraft:nether_sprouts" ) //TODO get ID??
public class ItemNetherSprouts extends ItemStack implements io.gomint.inventory.item.ItemNetherSprouts {

    @Override
    public String getBlockId() {
        return "minecraft:nether_sprouts";
    }

    @Override
    public ItemType getType() {
        return ItemType.NETHER_SPROUTS;
    }
}
