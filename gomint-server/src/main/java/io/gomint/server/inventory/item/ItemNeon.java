package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 276 )
public class ItemNeon extends ItemStack implements io.gomint.inventory.item.ItemNeon {

    @Override
    public String getBlockId() {
        return "minecraft:element_10";
    }

    @Override
    public ItemType getType() {
        return ItemType.NEON;
    }
}
