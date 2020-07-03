package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 557 ,sId = "minecraft:chiseled_nether_bricks" )
public class ItemChiseledNetherBrick extends ItemStack implements io.gomint.inventory.item.ItemChiseledNetherBrick {

    @Override
    public String getBlockId() {
        return "minecraft:chiseled_nether_bricks";
    }

    @Override
    public ItemType getType() {
        return ItemType.CHISELED_NETHER_BRICK;
    }
}
