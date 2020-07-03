package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 482, sId = "minecraft:warped_wart_block")
public class ItemNetherWarpedWart extends ItemStack implements io.gomint.inventory.item.ItemNetherWarpedWart {

    @Override
    public ItemType getType() {
        return ItemType.NETHER_WARPED_WART;
    }

    @Override
    public String getBlockId() {
        return "minecraft:warped_wart_block";
    }
}
