package io.gomint.server.inventory.item;

import io.gomint.inventory.item.ItemType;
import io.gomint.server.registry.RegisterInfo;

/**
 * @author Kaooot
 * @version 1.0
 */
@RegisterInfo( id = 267 )
public class ItemHydrogen extends ItemStack implements io.gomint.inventory.item.ItemHydrogen {

    @Override
    public String getBlockId() {
        return "minecraft:element_1";
    }

    @Override
    public ItemType getType() {
        return ItemType.HYDROGEN;
    }
}
