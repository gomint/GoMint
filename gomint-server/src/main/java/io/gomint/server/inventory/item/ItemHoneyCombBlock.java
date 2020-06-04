package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 474 )
public class ItemHoneyCombBlock extends ItemStack {

    @Override
    public String getBlockId() {
        return "minecraft:honeycomb_block";
    }

    @Override
    public ItemType getType() {
        return ItemType.HONEYCOMBBLOCK;
    }
}
