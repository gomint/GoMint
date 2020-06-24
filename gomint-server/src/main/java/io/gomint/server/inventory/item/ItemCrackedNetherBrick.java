package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 558 ,sId = "minecraft:cracked_nether_bricks" )
public class ItemCrackedNetherBrick extends ItemStack implements io.gomint.inventory.item.ItemCrackedNetherBrick {

    @Override
    public String getBlockId() {
        return "minecraft:cracked_nether_bricks";
    }

    @Override
    public ItemType getType() {
        return ItemType.CRACKED_NETHER_BRICK;
    }

}
