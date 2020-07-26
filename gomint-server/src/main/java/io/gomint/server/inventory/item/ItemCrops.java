package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wheat", id = 295)
public class ItemCrops extends ItemStack implements io.gomint.inventory.item.ItemCrops {

    @Override
    public ItemType getItemType() {
        return ItemType.CROPS;
    }

}
