package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:coral_block", id = -132)
public class ItemCoralBlock extends ItemStack implements io.gomint.inventory.item.ItemCoralBlock {

    @Override
    public ItemType getItemType() {
        return ItemType.CORAL_BLOCK;
    }

}
