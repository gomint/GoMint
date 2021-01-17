package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo(sId = "minecraft:honeycomb_block")
public class ItemHoneyCombBlock extends ItemStack< io.gomint.inventory.item.ItemHoneyCombBlock> implements io.gomint.inventory.item.ItemHoneyCombBlock {

    @Override
    public ItemType itemType() {
        return ItemType.HONEYCOMBBLOCK;
    }
}
