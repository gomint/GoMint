package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author KingAli
 * @version 1.0
 */
@RegisterInfo( id = 448 )
public class ItemLectern extends ItemStack implements io.gomint.inventory.item.ItemLectern {

    @Override
    public String getBlockId() {
        return "minecraft:lectern";
    }

    @Override
    public ItemType getType() {
        return ItemType.LECTERN;
    }
}
