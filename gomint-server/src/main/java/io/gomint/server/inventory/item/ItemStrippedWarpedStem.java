package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 496, sId = "minecraft:stripped_warped_stem")
public class ItemStrippedWarpedStem extends ItemStack {

    @Override
    public String getBlockId() {
        return "minecraft:stripped_warped_stem";
    }

    @Override
    public ItemType getType() {
        return ItemType.STRIPPED_WARPED_STEM;
    }
}
