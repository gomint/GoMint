package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(id = -220, sId = "minecraft:honey_block")
public class ItemHoneyBlock extends ItemStack< io.gomint.inventory.item.ItemHoneyBlock> implements io.gomint.inventory.item.ItemHoneyBlock {
    @Override
    public ItemType itemType() {
        return ItemType.HONEY_BLOCK;
    }
}
