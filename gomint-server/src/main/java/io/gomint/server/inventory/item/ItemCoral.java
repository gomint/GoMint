package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:coral", id = -131)
public class ItemCoral extends ItemStack implements io.gomint.inventory.item.ItemCoral {

    @Override
    public ItemType getItemType() {
        return ItemType.CORAL;
    }
}
