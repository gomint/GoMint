package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = 495, sId = "minecraft:stripped_crimson_stem")
public class ItemStrippedCrimsonStem extends ItemStack {

    @Override
    public String getBlockId() {
        return "minecraft:stripped_crimson_stem";
    }

    @Override
    public ItemType getType() {
        return ItemType.STRIPPED_CRIMSON_STEM;
    }
}
