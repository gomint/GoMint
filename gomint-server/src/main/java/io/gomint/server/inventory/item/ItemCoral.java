package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:coral")
public class ItemCoral extends ItemStack< io.gomint.inventory.item.ItemCoral> implements io.gomint.inventory.item.ItemCoral {

    @Override
    public ItemType itemType() {
        return ItemType.CORAL;
    }
}
