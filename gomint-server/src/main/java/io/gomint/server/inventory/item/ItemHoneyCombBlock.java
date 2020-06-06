package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = -221, sId = "minecraft:honeycomb_block" )
public class ItemHoneyCombBlock extends ItemStack implements io.gomint.inventory.item.ItemHoneyCombBlock {

    @Override
    public String getBlockId() {
        return "minecraft:honeycomb_block";
    }

    @Override
    public ItemType getType() {
        return ItemType.HONEYCOMBBLOCK;
    }
}
