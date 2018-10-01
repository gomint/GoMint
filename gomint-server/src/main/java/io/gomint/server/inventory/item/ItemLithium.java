package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 269 )
public class ItemLithium extends ItemStack implements io.gomint.inventory.item.ItemLithium {

    @Override
    public String getBlockId() {
        return "minecraft:element_3";
    }

    @Override
    public ItemType getType() {
        return ItemType.LITHIUM;
    }
}
