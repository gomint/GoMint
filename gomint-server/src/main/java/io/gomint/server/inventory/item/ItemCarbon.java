package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 272 )
public class ItemCarbon extends ItemStack implements io.gomint.inventory.item.ItemCarbon {

    @Override
    public String getBlockId() {
        return "minecraft:element_6";
    }

    @Override
    public ItemType getType() {
        return ItemType.CARBON;
    }
}
