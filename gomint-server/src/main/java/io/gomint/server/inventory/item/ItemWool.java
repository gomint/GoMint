package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author geNAZt
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:wool", id = 35)
public class ItemWool extends ItemStack implements io.gomint.inventory.item.ItemWool {

    @Override
    public ItemType getType() {
        return ItemType.WOOL;
    }

}
