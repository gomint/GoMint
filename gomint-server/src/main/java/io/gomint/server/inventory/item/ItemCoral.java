package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = -131 )
public class ItemCoral extends ItemStack implements io.gomint.inventory.item.ItemCoral {

    @Override
    public String getBlockId() {
        return "minecraft:coral";
    }

    @Override
    public ItemType getType() {
        return ItemType.CORAL;
    }
}
