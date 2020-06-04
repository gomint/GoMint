package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 473 )
public class ItemHoneyBlock extends ItemStack implements io.gomint.inventory.item.ItemHoneyBlock {

    @Override
    public String getBlockId() {
        return "minecraft:honey_block";
    }

    @Override
    public ItemType getType() {
        return ItemType.HONEY_BLOCK;
    }
}
