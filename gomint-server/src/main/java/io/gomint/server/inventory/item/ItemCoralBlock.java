package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:coral_block")
public class ItemCoralBlock extends ItemStack< io.gomint.inventory.item.ItemCoralBlock> implements io.gomint.inventory.item.ItemCoralBlock {

    @Override
    public ItemType itemType() {
        return ItemType.CORAL_BLOCK;
    }

}
