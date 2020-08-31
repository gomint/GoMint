package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(id = 38, sId = "minecraft:red_flower", def = true)
@RegisterInfo(id = -216, sId = "minecraft:wither_rose")
public class ItemFlower extends ItemStack implements io.gomint.inventory.item.ItemFlower {

    @Override
    public ItemType getItemType() {
        return ItemType.FLOWER;
    }

}
